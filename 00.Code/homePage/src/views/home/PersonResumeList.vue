<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.username"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="面试流程">
              <j-dict-select-tag
                v-model="queryParam.interviewState"
                placeholder="请输入面试流程"
                dictCode="person_resume_status"
              />
            </a-form-item>
          </a-col>

          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="学历">
                <a-input placeholder="请输入学历" v-model="queryParam.education"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="工作年限">
                <a-input placeholder="请输入工作年限" v-model="queryParam.workYear"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="推荐人">
                <a-input placeholder="请输入推荐人" v-model="queryParam.recomendPerson"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('简历管理')">导出</a-button>
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
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="showResumeDetalsList(record)">面试明细</a>
          <a-divider type="vertical" />
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
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <personResume-modal ref="modalForm" @ok="modalFormOk"></personResume-modal>

    <PersonResumeDetailsList ref="PersonResumeDetailsList"></PersonResumeDetailsList>
  </a-card>
</template>

<script>
import PersonResumeModal from './modules/PersonResumeModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import PersonResumeDetailsList from './PersonResumeDetailsList'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'PersonResumeList',
  mixins: [JeecgListMixin],
  components: {
    PersonResumeModal,
    PersonResumeDetailsList
  },
  data() {
    return {
      description: '简历管理管理页面',
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
          title: '姓名',
          align: 'center',
          dataIndex: 'username'
        },
        {
          title: '学历',
          align: 'center',
          dataIndex: 'education'
        },
        {
          title: '工作年限',
          align: 'center',
          dataIndex: 'workYear'
        },
        {
          title: '推荐人',
          align: 'center',
          dataIndex: 'recomendPerson'
        },
        {
          title: '面试官',
          align: 'center',
          dataIndex: 'interviewer'
        },
        {
          title: '面试流程',
          align: 'center',
          dataIndex: 'interviewState',
          customRender: (text, record, index) => {
            return filterDictText(this.sys_departDictOptions, text)
          }
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      sys_departDictOptions: [],
      url: {
        list: '/integrated/personResume/list',
        delete: '/integrated/personResume/delete',
        deleteBatch: '/integrated/personResume/deleteBatch',
        exportXlsUrl: 'integrated/personResume/exportXls',
        importExcelUrl: 'integrated/personResume/importExcel'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    showResumeDetalsList(record) {
      this.$refs.PersonResumeDetailsList.showResumeDetailsList(record.id)
    },
    initDictConfig() {
      initDictOptions('person_resume_status').then(res => {
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