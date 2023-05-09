<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="员工">
              <a-input placeholder="请输入员工" v-model="queryParam.staffName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="开始时间">
                <a-input placeholder="请输入开始时间" v-model="queryParam.startTime"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="结束时间">
                <a-input placeholder="请输入结束时间" v-model="queryParam.endTime"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="请假天数">
                <a-input placeholder="请输入请假天数" v-model="queryParam.days"></a-input>
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
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('请假')">导出</a-button>
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
          <a-popconfirm title="确定提交吗吗?" @confirm="() => commitEnginer(record)">
            <a>提交</a>
          </a-popconfirm>
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
    <processLeaveBill-modal ref="modalForm" @ok="modalFormOk"></processLeaveBill-modal>
  </a-card>
</template>

<script>
import ProcessLeaveBillModal from './modules/ProcessLeaveBillModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { axios } from '@/utils/request'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'


export default {
  name: 'OverTimeList',
  mixins: [JeecgListMixin],
  components: {
    ProcessLeaveBillModal
  },
  data() {
    return {
      description: '请假管理页面',
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
          title: '请假类型',
          align: 'center',
          dataIndex: 'leaveType',
          customRender: (text, record, index) => {
            return filterDictText(this.sys_departDictOptions, text)
          }
        },
        {
          title: '请假天数',
          align: 'center',
          dataIndex: 'days'
        },
        {
          title: '开始时间',
          align: 'center',
          dataIndex: 'startTime'
        },
        {
          title: '结束时间',
          align: 'center',
          dataIndex: 'endTime'
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
      sys_departDictOptions:[],
      url: {
        list: '/integrated/processLeaveBill/list',
        delete: '/integrated/processLeaveBill/delete',
        deleteBatch: '/integrated/processLeaveBill/deleteBatch',
        exportXlsUrl: 'integrated/processLeaveBill/exportXls',
        importExcelUrl: 'integrated/processLeaveBill/importExcel',
        commitEnginerUrl: 'workflow/engine/default/process-definition/key/Process_leave_bill/start'
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
      console.log(row.id)
      let url = this.url.commitEnginerUrl
      var obj = { businessKey: row.id, relationCode: 'process_leave_bill' }
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
          console.log(res)
          if (res.success == true) {
            this.$message.info('提交成功！')
          } else {
            this.$message.error(res.message)
          }
        })
    },
    initDictConfig() {
      initDictOptions('leave_type').then(res => {
        if (res.success) {
          this.sys_departDictOptions = res.result
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>