<template>
  <a-modal
    :title="'测量点'"
    @ok="closedetails"
    @cancel="closedetails"
    :width="1300"
    :visible="detailsvisible"
    cancelText="关闭"
  >
    <a-card id="cardclass" :bordered="false">
      <!-- 查询区域 -->
      <!-- <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="24">
            <a-col :md="6" :sm="8">
              <a-form-item label="测量点编号">
                <a-input placeholder="请输入测量点编号" v-model="queryParam.pointNo"></a-input>
                <a-input type="hidden" v-model="queryParam.productId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="测量点名称">
                <a-input placeholder="请输入测量点名称" v-model="queryParam.measureName"></a-input>
              </a-form-item>
            </a-col>
            <template v-if="toggleSearchStatus">
              <a-col :md="6" :sm="8">
                <a-form-item label="备注">
                  <a-input placeholder="请输入备注" v-model="queryParam.memo"></a-input>
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
      </div> -->

      <!-- 操作按钮区域 -->
      <div class="table-operator" style="margin-top:-25px;">
        <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
        <!-- <a-button type="primary" icon="download" @click="handleExportXls('测量点')">导出</a-button> -->
        <!-- <a-upload
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
        <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
          <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
          <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
          <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        </div>

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
      <measurePoints-modal ref="modalForm" @ok="modalFormOk"></measurePoints-modal>
    </a-card>
  </a-modal>
</template>

<script>
import MeasurePointsModal from './modules/MeasurePointsModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'

export default {
  name: 'MeasurePointsList',
  mixins: [JeecgListMixin],
  components: {
    MeasurePointsModal
  },
  data() {
    return {
      description: '测量点管理页面',
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
          title: '测量点编号',
          align: 'center',
          dataIndex: 'pointNo'
        },
        {
          title: '测量点',
          align: 'center',
          dataIndex: 'measureName'
        },
        {
          title: '最新值',
          align: 'center',
          dataIndex: 'lastValue'
        },
        {
          title: '最新值时间',
          align: 'center',
          dataIndex: 'lastTime'
        },

        {
          title: '最小值',
          align: 'center',
          dataIndex: 'valueMin'
        },
        {
          title: '最大值',
          align: 'center',
          dataIndex: 'valueMax'
        },
        {
          title: '单位',
          align: 'center',
          dataIndex: 'unit'
        },

        {
          title: '备注',
          align: 'center',
          dataIndex: 'memo'
        },

        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/integrated/measurePoints/list',
        delete: '/integrated/measurePoints/delete',
        deleteBatch: '/integrated/measurePoints/deleteBatch',
        exportXlsUrl: 'integrated/measurePoints/exportXls',
        importExcelUrl: 'integrated/measurePoints/importExcel'
      },
      detailsvisible: false
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    closedetails() {
      this.detailsvisible = false
    },
    showpoint(productId) {
      this.queryParam.productId = productId
      this.detailsvisible = true
      this.searchQuery()
    },
    handleAdd: function() {
      this.$refs.modalForm.add(this.queryParam.productId)
      this.$refs.modalForm.title = '新增'
      this.$refs.modalForm.disableSubmit = false
    }
  }
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';
 #cardclass .ant-card-body {
  padding: 0px 32px !important;
}
</style>