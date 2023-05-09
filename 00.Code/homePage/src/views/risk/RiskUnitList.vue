<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="公司编号">
              <a-input placeholder="请输入公司编号" v-model="queryParam.companyid"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="名称">
              <a-input placeholder="请输入名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('风险单元')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
          <a-icon type="delete"/>删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        <span>已选择</span>
        <a style="font-weight: 600">
          {{ selectedRowKeys.length }}
        </a>
        <span>项</span>
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
        @change="handleTableChange"
        :customRow="clickThenCheck">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->
    
    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="风险分析" :key="refKeys[0]" :forceRender="true">
        <riskAnalysis-List ref="RiskAnalysisList"></riskAnalysis-List>
      </a-tab-pane>
      <a-tab-pane tab="风险辨识" :key="refKeys[1]" :forceRender="true">
        <riskIdentification-List ref="RiskIdentificationList"></riskIdentification-List>
      </a-tab-pane>
      <!-- <a-tab-pane tab="管控措施" :key="refKeys[2]" :forceRender="true">
        <riskMeasures-List ref="RiskMeasuresList"></riskMeasures-List>
      </a-tab-pane> -->
      <a-tab-pane tab="管控措施" :key="refKeys[3]" :forceRender="true">
        <riskItem-List ref="RiskItemList"></riskItem-List>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <riskUnit-modal ref="modalForm" @ok="modalFormOk"/>

  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import RiskUnitModal from './modules/RiskUnitModal'
  import {deleteAction} from '@/api/manage'
  import RiskAnalysisList from './RiskAnalysisList'
  import RiskAnalysisModal from './modules/RiskAnalysisModal'
  import RiskIdentificationList from './RiskIdentificationList'
  import RiskIdentificationModal from './modules/RiskIdentificationModal'
  import RiskMeasuresList from './RiskMeasuresList'
  import RiskMeasuresModal from './modules/RiskMeasuresModal'
  import RiskItemList from './RiskItemList'
  import RiskItemModal from './modules/RiskItemModal'

  export default {
    name: "RiskUnitList",
    mixins: [JeecgListMixin],
    components: {
      RiskUnitModal,
      RiskAnalysisModal,
      RiskAnalysisList,
      RiskIdentificationModal,
      RiskIdentificationList,
      RiskMeasuresModal,
      RiskMeasuresList,
      RiskItemModal,
      RiskItemList,
    },
    data () {
      return {
        refKeys: ['riskAnalysis', 'riskIdentification', 'riskMeasures', 'riskItem', ],
        description: '风险单元管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender:function (t, r, index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title: '公司名称',
            align:"center",
            dataIndex: 'companyid'
          },
          {
            title: '单元类型',
            align:"center",
            dataIndex: 'unittype'
          },
          
          {
            title: '责任人',
            align:"center",
            dataIndex: 'personliable'
          },
          {
            title: '管控部门',
            align:"center",
            dataIndex: 'chargedepartment'
          },
          {
            title: '管控级别',
            align:"center",
            dataIndex: 'unitlevel'
          },
          {
            title: '创建时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
 
        //操作url
        type: "radio",
        url: {
          list: "/risk/riskUnit/list",
          delete: "/risk/riskUnit/delete",
          deleteBatch: "/risk/riskUnit/deleteBatch",
          exportXlsUrl: "risk/riskUnit/exportXls",
          importExcelUrl: "risk/riskUnit/importExcel",
         },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
		clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        };
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        this.$refs.RiskAnalysisList.getMain(this.selectedRowKeys[0]);
        this.$refs.RiskIdentificationList.getMain(this.selectedRowKeys[0]);
        // this.$refs.RiskMeasuresList.getMain(this.selectedRowKeys[0]);
        this.$refs.RiskItemList.getMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.RiskAnalysisList.queryParam.mainId = null;
        this.$refs.RiskAnalysisList.loadData();
        this.$refs.RiskAnalysisList.selectedRowKeys = [];
        this.$refs.RiskAnalysisList.selectionRows = [];
        this.$refs.RiskIdentificationList.queryParam.mainId = null;
        this.$refs.RiskIdentificationList.loadData();
        this.$refs.RiskIdentificationList.selectedRowKeys = [];
        this.$refs.RiskIdentificationList.selectionRows = [];
        // this.$refs.RiskMeasuresList.queryParam.mainId = null;
        // this.$refs.RiskMeasuresList.loadData();
        // this.$refs.RiskMeasuresList.selectedRowKeys = [];
        // this.$refs.RiskMeasuresList.selectionRows = [];
        this.$refs.RiskItemList.queryParam.mainId = null;
        this.$refs.RiskItemList.loadData();
        this.$refs.RiskItemList.selectedRowKeys = [];
        this.$refs.RiskItemList.selectionRows = [];
      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.RiskAnalysisList.loadData();
            this.$refs.RiskIdentificationList.loadData();
            // this.$refs.RiskMeasuresList.loadData();
            this.$refs.RiskItemList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.RiskAnalysisList.queryParam.mainId = null;
        this.$refs.RiskAnalysisList.loadData();
        this.$refs.RiskAnalysisList.selectedRowKeys = [];
        this.$refs.RiskAnalysisList.selectionRows = [];
        this.$refs.RiskIdentificationList.queryParam.mainId = null;
        this.$refs.RiskIdentificationList.loadData();
        this.$refs.RiskIdentificationList.selectedRowKeys = [];
        this.$refs.RiskIdentificationList.selectionRows = [];
        // this.$refs.RiskMeasuresList.queryParam.mainId = null;
        // this.$refs.RiskMeasuresList.loadData();
        // this.$refs.RiskMeasuresList.selectedRowKeys = [];
        // this.$refs.RiskMeasuresList.selectionRows = [];
        this.$refs.RiskItemList.queryParam.mainId = null;
        this.$refs.RiskItemList.loadData();
        this.$refs.RiskItemList.selectedRowKeys = [];
        this.$refs.RiskItemList.selectionRows = [];
        this.loadData();
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>