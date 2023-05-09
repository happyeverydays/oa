<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <!-- <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="班种">
              <a-input placeholder="请输入班种" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="上班时间">
              <a-input placeholder="请输入上班时间" v-model="queryParam.startTime"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="下班时间">
                <a-input placeholder="请输入下班时间" v-model="queryParam.endTime"></a-input>
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
    </div>-->

    <!-- 操作按钮区域 -->
    <!-- <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      
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
    </div>-->

    <!-- table区域-begin -->
    <div>
      <!-- <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>-->

      <h2 style="margin:0 auto;margin-bottom:5px;">上班加班配置</h2>

      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
        </span>
      </a-table>

      <h2 style="margin:0 auto;margin-bottom:5px;">迟到算旷工配置</h2>
      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns1"
        :dataSource="dataSource1"
        :loading="loading"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit1(record)">编辑</a>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <attendConfig-modal ref="modalForm" @ok="modalFormOk"></attendConfig-modal>

    <AttendConfigLateTimeModal ref="modalForm1"></AttendConfigLateTimeModal>
  </a-card>
</template>

<script>
import AttendConfigModal from './modules/AttendConfigModal'
import AttendConfigLateTimeModal from './modules/AttendConfigLateTimeModal'


import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { getAction } from '@/api/manage'

export default {
  name: 'AttendConfigList',
  mixins: [JeecgListMixin],
  components: {
    AttendConfigModal,
    AttendConfigLateTimeModal
  },
  data() {
    return {
      description: '打卡配置管理页面',
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
          title: '班种',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '上班时间',
          align: 'center',
          dataIndex: 'startTime'
        },
        {
          title: '下班时间',
          align: 'center',
          dataIndex: 'endTime'
        },

        {
          title: '编辑',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      columns1: [
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
          title: '名称',
          align: 'center',
          dataIndex: 'itemText'
        },
        {
          title: '时间(h)',
          align: 'center',
          dataIndex: 'itemValue'
        },

        {
          title: '编辑',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      dataSource1: [],
      url: {
        list: '/attendConfig/list',
        late_time_list: '/attendConfig/getAttendConfList?code=late_time',
        delete: '/attendConfig/delete',
        deleteBatch: '/attendConfig/deleteBatch',
        exportXlsUrl: 'attendConfig/exportXls',
        importExcelUrl: 'attendConfig/importExcel'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  created() {
    this.loadlatetimeconf()
  },
  methods: {
    loadlatetimeconf() {
      getAction(this.url.late_time_list, '').then(res => {
        if (res.success) {
          this.dataSource1 = res.result.records
          this.ipagination.total = res.result.total
        }
      })
    },
    handleEdit1: function (record) {
      this.$refs.modalForm1.edit(record);
      this.$refs.modalForm1.title = "编辑";
    },
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>