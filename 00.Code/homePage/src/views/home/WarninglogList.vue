<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="门禁点ID号">
              <a-input placeholder="请输入门禁点ID号" v-model="queryParam.DoorNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="报警开始时间">
              <a-date-picker @change="onChangebeginDateTime" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="报警结束时间">
              <a-date-picker @change="onChangeendDateTime" />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus"></template>
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
       
        rowKey="device_ID"
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
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'

export default {
  name: 'WarninglogList',
  mixins: [JeecgListMixin],
  components: {},
  data() {
    return {
      description: '门管理页面',
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
          title: '报警记录流水号',
          align: 'center',
          dataIndex: 'warning_ID'
        },

        {
          title: '门禁点 ID 号',
          align: 'center',
          dataIndex: 'device_ID'
        },

        {
          title: '报警类型',
          align: 'center',
          dataIndex: 'warning_Type'
        },

        {
          title: '报警时间',
          align: 'center',
          dataIndex: 'warning_DateTime'
        }

        // ,
        // {
        //   title: "操作",
        //   dataIndex: "action",
        //   align: "center",
        //   scopedSlots: { customRender: "action" }
        // }
      ],
      url: {
        list: '/door/getWarninglogList',
        delete: '/integrated/door/delete',
        deleteBatch: '/integrated/door/deleteBatch',
        exportXlsUrl: 'integrated/door/exportXls',
        importExcelUrl: 'integrated/door/importExcel'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    onChangebeginDateTime(date, dateString) {
      this.queryParam.beginDateTime = dateString
    },
    onChangeendDateTime(date, dateString) {
      this.queryParam.endDateTime = dateString
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>