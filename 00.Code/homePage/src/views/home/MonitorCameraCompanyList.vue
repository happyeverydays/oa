<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8" style="display:none;">
            <a-form-item label="公司">
              <a-input placeholder="请输入companyId" v-model="queryParam.companyId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="相机">
              <a-input placeholder="请输入相机" v-model="queryParam.cname"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="类型">
              <j-dict-select-tag
                v-model="queryParam.ctype"
                placeholder="请输入类型"
                dictCode="split_creen_type"
              />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="流地址">
                <a-input placeholder="请输入流地址" v-model="queryParam.cameraUrl"></a-input>
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
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('分屏')">导出</a-button>
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
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <monitorCameraCompany-modal ref="modalForm" @ok="modalFormOk"></monitorCameraCompany-modal>
  </a-card>
</template>

<script>
import MonitorCameraCompanyModal from './modules/MonitorCameraCompanyModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'MonitorCameraCompanyList',
  mixins: [JeecgListMixin],
  components: {
    MonitorCameraCompanyModal
  },
  data() {
    return {
      description: '分屏管理页面',
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
          title: '公司名称',
          align: 'center',
          dataIndex: 'companyId',
          customRender: (text, record, index) => {
            return filterDictText(this.sys_departDictOptions, text)
          }
        },
        {
          title: '相机名称',
          align: 'center',
          dataIndex: 'cname'
        },
        {
          title: '流地址',
          align: 'center',
          dataIndex: 'cameraUrl'
        },
        {
          title: '类型',
          align: 'center',
          dataIndex: 'ctype',
          customRender: (text, record, index) => {
            return filterDictText(this.split_creen_typeDictOptions, text)
          }
        },
        {
          title: '排序',
          align: 'center',
          dataIndex: 'sort'
        },

        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      split_creen_typeDictOptions: [],
      sys_departDictOptions: [],
      companyId: null,
      url: {
        list: '/monitorCameraCompany/list',
        delete: '/monitorCameraCompany/delete',
        deleteBatch: '/monitorCameraCompany/deleteBatch',
        exportXlsUrl: 'monitorCameraCompany/exportXls',
        importExcelUrl: 'monitorCameraCompany/importExcel'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {
      initDictOptions('split_creen_type').then(res => {
        if (res.success) {
          this.split_creen_typeDictOptions = res.result
        }
      })

      initDictOptions('sys_depart,depart_name,id').then(res => {
        if (res.success) {
          this.sys_departDictOptions = res.result
        }
      })
    },
    beforeLoad() {
      this.queryParam.companyId = this.getcompanyid()
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>