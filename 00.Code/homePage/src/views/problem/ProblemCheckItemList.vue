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
    <ProblemCheckItem-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>
  import ProblemCheckItemModal from './modules/ProblemCheckItemModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'

  export default {
    name: "ProblemCheckItemList",
    mixins: [JeecgListMixin],
    components: {
      ProblemCheckItemModal,
    },
    data() {
      return {
        description: '信息',
        // 表头
        columns: [
          {
            title: '名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title: '描述',
            align:"center",
            dataIndex: 'description'
          },
          {
            title: '来源',
            align:"center",
            dataIndex: 'source'
          },
          {
            title: '条款',
            align:"center",
            dataIndex: 'clause'
          },
          {
            title: '检查频次',
            align:"center",
            dataIndex: 'checkFrequency'
          },
          {
            title: '检查岗位',
            align:"center",
            dataIndex: 'checkPosition'
          },
          {
            title: '隐患级别',
            align:"center",
            dataIndex: 'problemLevel'
          },
          {
            title: '发生隐患时的描述',
            align:"center",
            dataIndex: 'troubleContent'
          },
          {
            title: '正常情况时的描述',
            align:"center",
            dataIndex: 'normalContent'
          },
          {
            title: '检查项目',
            align:"center",
            dataIndex: 'checkItem'
          },
          {
            title: '备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title: '所属部门',
            align:"center",
            dataIndex: 'sysOrgCode'
          },
         {
          title: '操作',
          key: 'operation',
          align: "center",
          width: 130,
          scopedSlots: {customRender: 'action'},
        }],
        url: {
          list: "/problem/problemCheckPoint/listProblemCheckItemByMainId",
          delete: "/problem/problemCheckPoint/deleteProblemCheckItem",
          deleteBatch: "/problem/problemCheckPoint/deleteBatchProblemCheckItem",
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
        this.$refs.modalForm.title = "添加危险源检查条目";
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
