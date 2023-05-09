<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="开始时间">
              <j-date
                placeholder="请选择开始时间"
                v-model="queryParam.starttime"
                :trigger-change="true"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="结束时间">
              <j-date
                placeholder="请选择结束时间"
                v-model="queryParam.endtime"
                :trigger-change="true"
                style="width: 100%"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.username"></a-input>
            </a-form-item>
          </a-col>

          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="部门">
                <a-input placeholder="请输入部门" v-model="queryParam.deptName"></a-input>
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
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('考勤记录')">导出</a-button>
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
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <attendRecord-modal ref="modalForm" @ok="modalFormOk"></attendRecord-modal>
  </a-card>
</template>

<script>
import AttendRecordModal from './modules/AttendUserConfigModel'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'AttendRecordList',
  mixins: [JeecgListMixin],
  components: {
    AttendRecordModal
  },
  data() {
    return {
      description: '考勤记录管理页面',
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
          title: '部门',
          align: 'center',
          dataIndex: 'deptName'
        },
        {
          title: '工号',
          align: 'center',
          dataIndex: 'jobNumber'
        },
        {
          title: '姓名',
          align: 'center',
          dataIndex: 'username'
        },

        {
          title: '有效考勤天数',
          align: 'center',
          dataIndex: 'acceptcard'
        },
        {
          title: '异常考勤次数',
          align: 'center',
          dataIndex: 'mindt'
        },
        {
          title: '迟到次数',
          align: 'center',
          dataIndex: 'mindtarrivedays'
        },
        {
          title: '迟到总工时',
          align: 'center',
          dataIndex: 'mindtarrivedt'
        },
        {
          title: '早退次数',
          align: 'center',
          dataIndex: 'maxdtleavedays'
        },
        {
          title: '早退总工时',
          align: 'center',
          dataIndex: 'maxdtleavedt'
        },
        {
          title: '旷工天数',
          align: 'center',
          dataIndex: 'absenceDuty'
        },
        {
          title: '请假天数',
          align: 'center',
          dataIndex: 'mindt5'
        },
        {
          title: '修年假天数',
          align: 'center',
          dataIndex: 'mindt6'
        },
        {
          title: '可调休时长',
          align: 'center',
          dataIndex: 'addhours'
        }
        // {
        //   title: '操作',
        //   dataIndex: 'action',
        //   align: 'center',
        //   scopedSlots: { customRender: 'action' }
        // }
      ],
      atend_typeOptions: [],
      deviceOptions: [],
      queryParam: { starttime: '2020-01-01', endtime: '2020-01-01' },
      url: {
        list: '/attendRecord/getAttendStaticsList',
        delete: '/attendRecord/delete',
        deleteBatch: '/attendRecord/deleteBatch',
        exportXlsUrl: 'attendRecord/exportXls',
        importExcelUrl: 'attendRecord/importExcel'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },

  methods: {
    beforeLoad() {
      this.queryParam.starttime = this.getCurrentMonthFirst()
      this.queryParam.endtime = this.getCurrentMonthLast()
    },
    initDictConfig() {
      initDictOptions('attend_config,name,id').then(res => {
        if (res.success) {
          this.atend_typeOptions = res.result
        }
      })

      initDictOptions('attend_device,name,id').then(res => {
        if (res.success) {
          this.deviceOptions = res.result
        }
      })
    },
    getCurrentMonthFirst() {
      var date = new Date()
      date.setDate(1)
      var month = parseInt(date.getMonth() + 1)
      var day = date.getDate()
      if (month < 10) {
        month = '0' + month
      }
      if (day < 10) {
        day = '0' + day
      }
      return date.getFullYear() + '-' + month + '-' + day
    },
    getCurrentMonthLast() {
      var date = new Date()
      var currentMonth = date.getMonth()
      var nextMonth = ++currentMonth
      var nextMonthFirstDay = new Date(date.getFullYear(), nextMonth, 1)
      var oneDay = 1000 * 60 * 60 * 24
      var lastTime = new Date(nextMonthFirstDay - oneDay)
      var month = parseInt(lastTime.getMonth() + 1)
      var day = lastTime.getDate()
      if (month < 10) {
        month = '0' + month
      }
      if (day < 10) {
        day = '0' + day
      }
      return date.getFullYear() + '-' + month + '-' + day
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>