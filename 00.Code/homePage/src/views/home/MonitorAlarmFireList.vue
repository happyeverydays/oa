<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="地点位置">
              <a-input placeholder="请输入地点位置" v-model="queryParam.positionName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="6">
            <a-form-item label="开始时间">
              <JDate placeholder="请输入开始时间" v-model="queryParam.createTime_begin" />
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="6">
            <a-form-item label="结束时间">
              <JDate placeholder="请输入结束时间" v-model="queryParam.createTime_end" />
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="内容">
                <a-input placeholder="请输入内容" v-model="queryParam.content"></a-input>
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
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('告警')">导出</a-button>
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
    <monitorAlarm-modal ref="modalForm" @ok="modalFormOk"></monitorAlarm-modal>
  </a-card>
</template>

<script>
import MonitorAlarmModal from './modules/MonitorAlarmModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'MonitorAlarmList',
  mixins: [JeecgListMixin],
  components: {
    MonitorAlarmModal
  },
  data() {
    return {
      description: '告警管理页面',
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
          title: '公司',
          align: 'center',
          dataIndex: 'companyId',
          customRender: (text, record, index) => {
            return filterDictText(this.sys_departDictOptions, text)
          }
        },
        {
          title: '地点位置',
          align: 'center',
          dataIndex: 'positionName'
        },
        {
          title: '摄像机',
          align: 'center',
          dataIndex: 'cameraId'
        },

        {
          title: '内容',
          align: 'center',
          dataIndex: 'content'
        },
        {
          title: '视频',
          align: 'center',
          dataIndex: 'videoId'
        },
        {
          title: '时间',
          align: 'center',
          dataIndex: 'createTime'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      sys_departDictOptions: [],
      url: {
        list: '/monitorAlarmFire/list',
        delete: '/monitorAlarmFire/delete',
        deleteBatch: '/monitorAlarmFire/deleteBatch',
        exportXlsUrl: 'monitorAlarmFire/exportXls',
        importExcelUrl: 'monitorAlarmFire/importExcel'
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
      initDictOptions('sys_depart,depart_name,id').then(res => {
        if (res.success) {
          this.sys_departDictOptions = res.result
        }
      })
    },
    beforeLoad() {
      this.queryParam.companyId = this.getcompanyid()
    },
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>