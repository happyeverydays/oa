<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator" :md="24" :sm="24" style="margin: 0px 0px 20px 0px">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
              </a-menu-item>
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
    <RiskAnalysis-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>
  import RiskAnalysisModal from './modules/RiskAnalysisModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'

  export default {
    name: "RiskAnalysisList",
    mixins: [JeecgListMixin],
    components: {
      RiskAnalysisModal,
    },
    data() {
      return {
        description: '信息',
        // 表头
        columns: [
          {
            title: '公司编号',
            align:"center",
            dataIndex: 'companyid'
          },
          {
            title: '检查项目',
            align:"center",
            dataIndex: 'inspectionproject'
          },
          {
            title: '检查标准',
            align:"center",
            dataIndex: 'inspectionstandard'
          },
          {
            title: '本次分析前事故次数',
            align:"center",
            dataIndex: 'accidentcount'
          },
          {
            title: '事故发生可能性L',
            align:"center",
            dataIndex: 'lecL'
          },
          {
            title: '人员暴露频繁程度E',
            align:"center",
            dataIndex: 'lecE'
          },
          {
            title: '事故后果严重性C',
            align:"center",
            dataIndex: 'lecC'
          },
          {
            title: '风险值D',
            align:"center",
            dataIndex: 'lecD'
          },
          {
            title: '风险等级',
            align:"center",
            dataIndex: 'riskgrade'
          },
          {
            title: '评价等级',
            align:"center",
            dataIndex: 'evaluationgrade'
          },
          {
            title: '管控级别',
            align:"center",
            dataIndex: 'controllevel'
          },
          {
            title: '管控措施',
            align:"center",
            dataIndex: 'proposestep'
          },
          {
            title: '是否正常',
            align:"center",
            dataIndex: 'isok'
          },
          {
            title: '是否检查',
            align:"center",
            dataIndex: 'ischeck'
          },
          {
            title: '频次',
            align:"center",
            dataIndex: 'frequency'
          },
          {
            title: '检查岗位',
            align:"center",
            dataIndex: 'inspectionpost'
          },
          {
            title: '分析时间',
            align:"center",
            dataIndex: 'analysistime'
          },
          {
            title: '参与人',
            align:"center",
            dataIndex: 'participant'
          },
          {
            title: '备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title: '附件',
            align:"center",
            dataIndex: 'attachment'
          },
         {
          title: '操作',
          key: 'operation',
          align: "center",
          width: 130,
          scopedSlots: {customRender: 'action'},
        }],
        url: {
          list: "/risk/riskUnit/listRiskAnalysisByMainId",
          delete: "/risk/riskUnit/deleteRiskAnalysis",
          deleteBatch: "/risk/riskUnit/deleteBatchRiskAnalysis",
        }
      }
    },
    methods: {
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();
        getAction(this.url.list, {mainId: params.mainId}).then((res) => {
          if (res.success) {
            this.dataSource = res.result;
          } else {
            this.dataSource = null;
          }
        })
      },
      getMain(mainId) {
        this.queryParam.mainId = mainId;
        this.loadData(1);
      },
      handleAdd: function () {
        this.$refs.modalForm.add(this.queryParam.mainId);
        this.$refs.modalForm.title = "添加风险分析";
      },
    }
  }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>
