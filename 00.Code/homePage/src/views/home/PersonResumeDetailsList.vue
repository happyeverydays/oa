<template>
  <a-modal
    title="面试详情"
    :width="1600"
    :visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-card :bordered="false">
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="24">
            <a-col :md="6" :sm="8">
              <a-form-item label="面试官">
                <a-input placeholder="请输入面试官" v-model="queryParam.interviewerName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="面试评价">
                <a-input placeholder="请输入面试评价" v-model="queryParam.memo"></a-input>
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
        <!-- <a-button type="primary" icon="download" @click="handleExportXls('面试详情')">导出</a-button>
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

          <span slot="attachment" slot-scope="text, record">
            <a :href="record.attachmentStr">{{text}}</a>

          </span>
        </a-table>
      </div>
      <!-- table区域-end -->

      <!-- 表单区域 -->
      <personResumeDetails-modal ref="modalForm" @ok="modalFormOk"></personResumeDetails-modal>
    </a-card>
  </a-modal>
</template>

<script>
import PersonResumeDetailsModal from './modules/PersonResumeDetailsModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'

export default {
  name: 'PersonResumeDetailsList',
  mixins: [JeecgListMixin],
  components: {
    PersonResumeDetailsModal
  },
  data() {
    return {
      description: '面试详情管理页面',
      visible: false,
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
          title: '面试官工号',
          align: 'center',
          dataIndex: 'interviewerJobNumber'
        },
        {
          title: '面试官姓名',
          align: 'center',
          dataIndex: 'interviewerName'
        },
        {
          title: '面试评价',
          align: 'center',
          dataIndex: 'memo'
        },
        {
          title: '面试时间',
          align: 'center',
          dataIndex: 'interviewerDate'
        },
        {
          title: '附件',
          align: 'center',
          dataIndex: 'attachment',
          scopedSlots: { customRender: 'attachment' }
        },

        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/integrated/personResumeDetails/list',
        delete: '/integrated/personResumeDetails/delete',
        deleteBatch: '/integrated/personResumeDetails/deleteBatch',
        exportXlsUrl: 'integrated/personResumeDetails/exportXls',
        importExcelUrl: 'integrated/personResumeDetails/importExcel'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    showResumeDetailsList(id) {
      this.visible = true
    },
    handleOk() {
      this.visible = false
    },
    handleCancel() {
      this.visible = false
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>