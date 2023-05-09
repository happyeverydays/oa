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
          <template v-if="toggleSearchStatus"></template>
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
      <a-button type="primary" icon="download" @click="handleExportXls('加班单')">导出</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
    <processOverTime-modal ref="modalForm" @ok="modalFormOk"></processOverTime-modal>
  </a-card>
</template>

<script>
import ProcessOverTimeModal from './modules/ProcessOverTimeModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { postFormDataAction, putAction } from '@/api/manage'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'ProcessOverTimeList',
  mixins: [JeecgListMixin],
  components: {
    ProcessOverTimeModal
  },
  data() {
    return {
      description: '加班单管理页面',
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
          title: '员工',
          align: 'center',
          dataIndex: 'staffName'
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
          title: '加班时间',
          align: 'center',
          dataIndex: 'addHours'
        },
        {
          title: '原因',
          align: 'center',
          dataIndex: 'reason'
        },
        {
          title: '申请状态',
          align: 'center',
          dataIndex: 'processState',
          customRender: (text, record, index) => {
            return filterDictText(this.processStateOption, text)
          }
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
        list: '/processOverTime/list',
        delete: '/processOverTime/delete',
        deleteBatch: '/processOverTime/deleteBatch',
        exportXlsUrl: 'processOverTime/exportXls',
        importExcelUrl: 'processOverTime/importExcel'
      },
      processStateOption: []
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {
      initDictOptions('processState').then(res => {
        if (res.success) {
          this.processStateOption = res.result
        }
      })
    },
    commitEnginer(row) {
      if (row.processState != 0) {
        this.$message.error('流程已经提交，不可重复提交')
        return
      }

      let url = 'workflow/engine/default/process-definition/key/' + 'Process_plus_work' + '/start'
      var obj = { businessKey: row.id, relationCode: 'process_over_time' }
      postFormDataAction(url, obj).then(res => {
        if (res.success == true) {
          putAction('/processOverTime/edit', { id: row.id, processState: '1' })
          this.$message.info('提交成功！')
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