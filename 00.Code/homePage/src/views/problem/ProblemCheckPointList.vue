<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="24">
            <a-form-item label="编码">
              <a-input placeholder="请输入编码" v-model="queryParam.code"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="名称">
              <a-input placeholder="请输入名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="风险单元">
              <j-dict-select-tag v-model="queryParam.riskUnitId" placeholder="请选择风险单元" 
                 dictCode="risk_unit,name,id"/>
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
      <a-button type="primary" icon="download" @click="handleExportXls('危险源')">导出</a-button>
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
      <a-tab-pane tab="危险源检查条目" :key="refKeys[0]" :forceRender="true">
        <problemCheckItem-List ref="ProblemCheckItemList"></problemCheckItem-List>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <problemCheckPoint-modal ref="modalForm" @ok="modalFormOk"/>

  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ProblemCheckPointModal from './modules/ProblemCheckPointModal'
  import {deleteAction} from '@/api/manage'
  import ProblemCheckItemList from './ProblemCheckItemList'
  import ProblemCheckItemModal from './modules/ProblemCheckItemModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "ProblemCheckPointList",
    mixins: [JeecgListMixin],
    components: {
      ProblemCheckPointModal,
      ProblemCheckItemModal,
      ProblemCheckItemList,
    },
    data () {
      return {
        refKeys: ['problemCheckItem', ],
        description: '危险源管理页面',
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
            title: '编码',
            align:"center",
            dataIndex: 'code'
          },
          {
            title: '名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title: '风险单元编号',
            align:"center",
            dataIndex: 'riskUnitId_dictText'
          },
          {
            title: '风险级别',
            align:"center",
            dataIndex: 'level'
          },
          {
            title: '公司编号',
            align:"center",
            dataIndex: 'companyId'
          },
          {
            title: '大类',
            align:"center",
            dataIndex: 'category1_dictText'
          },
          {
            title: '中类',
            align:"center",
            dataIndex: 'category2_dictText'
          },
          {
            title: '小类',
            align:"center",
            dataIndex: 'category3_dictText'
          },
          {
            title: '部门',
            align:"center",
            dataIndex: 'depart'
          },
          {
            title: '位置',
            align:"center",
            dataIndex: 'location'
          },
          {
            title: '占地面积',
            align:"center",
            dataIndex: 'landArea'
          },
          {
            title: '区域结构',
            align:"center",
            dataIndex: 'structure'
          },
          {
            title: '巡检点类型',
            align:"center",
            dataIndex: 'spaceType'
          },
          {
            title: '火灾危险等级',
            align:"center",
            dataIndex: 'fireLevel'
          },
          {
            title: '建筑完成时间',
            align:"center",
            dataIndex: 'buildingFinishTime'
          },
          {
            title: '建筑投用时间',
            align:"center",
            dataIndex: 'buildingUseTime'
          },
          {
            title: '备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title: '负责人',
            align:"center",
            dataIndex: 'responsible'
          },
          {
            title: '附件',
            align:"center",
            dataIndex: 'attachment'
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
          list: "/problem/problemCheckPoint/list",
          delete: "/problem/problemCheckPoint/delete",
          deleteBatch: "/problem/problemCheckPoint/deleteBatch",
          exportXlsUrl: "/problem/problemCheckPoint/exportXls",
          importExcelUrl: "/problem/problemCheckPoint/importExcel",
         },
        dictOptions:{
         riskUnitId:[],
         category1:[],
         category2:[],
         category3:[],
        } 
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
        this.$refs.ProblemCheckItemList.getMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.ProblemCheckItemList.queryParam.mainId = null;
        this.$refs.ProblemCheckItemList.loadData();
        this.$refs.ProblemCheckItemList.selectedRowKeys = [];
        this.$refs.ProblemCheckItemList.selectionRows = [];
      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.ProblemCheckItemList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.ProblemCheckItemList.queryParam.mainId = null;
        this.$refs.ProblemCheckItemList.loadData();
        this.$refs.ProblemCheckItemList.selectedRowKeys = [];
        this.$refs.ProblemCheckItemList.selectionRows = [];
        this.loadData();
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>