<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="申请人">
              <a-input placeholder="请输入申请人" v-model="queryParam.staffUsername"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="总价">
              <a-input placeholder="请输入总价" v-model="queryParam.totalprice"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="原因">
                <a-input placeholder="请输入原因" v-model="queryParam.reason"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('报销')">导出</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>-->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete" />删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <!-- <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>-->

      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            v-if="record.processState==0"
            title="确定提交吗吗?"
            @confirm="() => commitEnginer(record)"
          >
            <a>提交</a>
          </a-popconfirm>
          <span v-else>已提交</span>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <processReimburse-modal ref="modalForm" @ok="modalFormOk"></processReimburse-modal>
  </a-card>
</template>

<script>
import ProcessReimburseModal from './modules/ProcessReimburseModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { axios } from '@/utils/request'
import { putAction } from '@/api/manage'

export default {
  name: 'ProcessReimburseList',
  mixins: [JeecgListMixin],
  components: {
    ProcessReimburseModal
  },
  data() {
    return {
      description: '报销管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
        {
          title: '工号',
          align: 'center',
          dataIndex: 'jobNumber'
        },
        {
          title: '申请人',
          align: 'center',
          dataIndex: 'staffName'
        },
        {
          title: '总金额',
          align: 'center',
          dataIndex: 'totalprice'
        },
        {
          title: '原因',
          align: 'center',
          dataIndex: 'reason'
        },
        {
          title: '申请时间',
          align: 'center',
          dataIndex: 'applyDate'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/processReimburse/list',
        edit: '/processReimburse/edit',
        delete: '/processReimburse/delete',
        deleteBatch: '/processReimburse/deleteBatch',
        exportXlsUrl: 'processReimburse/exportXls',
        importExcelUrl: 'processReimburse/importExcel',
        commitEnginerUrl: 'workflow/engine/default/process-definition/key/Process_Reimbursement/start'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    commitEnginer(row) {
      let url = this.url.commitEnginerUrl
      var obj = { businessKey: row.id, relationCode: 'process_reimburse' }
      let fd = new FormData()
      Object.keys(obj).forEach(function(key) {
        fd.append(key, obj[key])
      })
      axios
        .post(url, fd, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(res => {
          this.$message.info('提交成功！')
          if (res.success == true) {
            putAction(this.url.edit, { id: row.id, processState: '1' }).then(res => {
              this.loadData();
            })
          } else {
            this.$message.error(res.message)
          }
        })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>