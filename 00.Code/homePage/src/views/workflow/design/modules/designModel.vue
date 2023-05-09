<template>
  <a-modal
    :title="title"
    :visible="visible"
    @ok="handleOk"
    width="100%"
    :footer="null"
    style="top:0"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-tabs defaultActiveKey="1" @change="callback" tabPosition="left">
      <a-tab-pane tab="业务关联" key="1">
        <a-card>
          <a-row>
            <a-col :md="2" :sm="12" style="display:flex;justify-content:flex-start">
              <a-button type="primary" icon="plus" style="margin-left:0">新增</a-button>
            </a-col>
          </a-row>
          <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
            <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
            <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
            <a style="margin-left: 24px" @click="onClearSelected">清空</a>
          </div>
          <a-table
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            :bordered="true"
            :columns="columns"
            :dataSource="datas"
          >
            <template slot="action" slot-scope="text, record">
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
            </template>
          </a-table>
        </a-card>
      </a-tab-pane>
     
    </a-tabs>
  </a-modal>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
const columns = [
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
    title: '节点名称',
    align: 'center',
    dataIndex: 'companyId'
  },
  {
    title: '节点编码',
    align: 'center',
    dataIndex: 'areaId'
  },
  {
    title: 'PC表单地址',
    align: 'center',
    dataIndex: 'name'
  },
  {
    title: '移动',
    align: 'center',
    dataIndex: 'type'
  },
  {
    title: '操作',
    dataIndex: 'action',
    align: 'center',
    scopedSlots: { customRender: 'action' }
  }
]
const datas = [
  {
    companyId: 'task1569680619772',
    areaId: 'task1569680619772'
  }
]
export default {
  name: 'BuildingModal',
  mixins: [JeecgListMixin],
  components: {},
  data() {
    return {
      datas,
      columns,
      title: '操作',
      width: 800,
      confirmLoading: false,
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      }
    }
  },
  created() {
    
  },
  methods: {
    callback(key) {
      console.log(key)
    },
    onClearSelected() {
      this.selectedRowKeys = []
      this.selectionRows = []
    },
   
    loads() {
      this.visible = true
    },
    handleOk() {
      // this.confirmLoading = true;
      this.visible = false
    },
    handleCancel() {
      // this.confirmLoading = true;
      this.visible = false
    }
  }
}
</script>

<style lang="less" scoped>
/** Button按钮间距 */
.ant-btn {
  margin-left: 30px;
  margin-bottom: 30px;
  float: right;
}
</style>