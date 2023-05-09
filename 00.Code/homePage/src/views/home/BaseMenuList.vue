<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="名称">
              <a-input placeholder="请输入名称" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="地址">
              <a-input placeholder="请输入地址" v-model="queryParam.url"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="父id">
                <a-input placeholder="请输入父id" v-model="queryParam.pid"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="排序">
                <a-input placeholder="请输入排序" v-model="queryParam.sort"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('菜单')">导出</a-button>

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
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :defaultExpandedRowKeys="childrenColumnName"
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
    <baseMenu-modal ref="modalForm" @ok="modalFormOk"></baseMenu-modal>
  </a-card>
</template>

<script>
import BaseMenuModal from './modules/BaseMenuModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'
import { getAction } from '@/api/manage'

export default {
  name: 'BaseMenuList',
  mixins: [JeecgListMixin],
  components: {
    BaseMenuModal
  },
  data() {
    return {
      description: '菜单管理页面',
      childrenColumnName: ['title'],

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
          title: '唯一key',
          align: 'center',
          dataIndex: 'unikey'
        },

        
        {
          title: '名称',
          align: 'center',
          dataIndex: 'title'
        },
        {
          title: '地址',
          align: 'center',
          dataIndex: 'url'
        },
        {
          title: '父id',
          align: 'center',
          dataIndex: 'pid',
          customRender: (text, record, index) => {
            return filterDictText(this.pidOptions, text + '')
          }
        },
        {
          title: '排序',
          align: 'center',
          dataIndex: 'sort'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/baseMenu/list',
        delete: '/baseMenu/delete',
        deleteBatch: '/baseMenu/deleteBatch',
        exportXlsUrl: 'baseMenu/exportXls',
        importExcelUrl: 'baseMenu/importExcel'
      },
      pidOptions: []
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  created() {
    this.queryParam.type = 1
  },
  methods: {
    initDictConfig() {
      initDictOptions('base_menu,title,id').then(res => {
        if (res.success) {
          this.pidOptions = res.result
        }
      })
    },
     loadData(arg) {
      if (arg == 1) {
        this.ipagination.current = 1
      }
      this.loading = true
      this.expandedRowKeys = []
      let params = this.getQueryParams();
      params.type=1;
      return new Promise(resolve => {
        getAction(this.url.list, params).then(res => {
          if (res.success) {
            let result = res.result
            if (Number(result.total) > 0) {
              this.ipagination.total = Number(result.total)
              var dataSource = this.filterdatasource(res.result.records)
              this.dataSource = dataSource
              resolve()
            } else {
              this.ipagination.total = 0
              this.dataSource = []
            }
          } else {
            this.$message.warning(res.message)
          }
          this.loading = false
        })
      })
    },
    filterdatasource(rows) {
      let _this = this
      let list = rows.map(item => {
        let _item = _this.filterdataobj(item)
        return _item
      })
      return list
    },
    filterdataobj(rows1) {
      if (rows1.children.length > 0) {
        this.filterdatasource(rows1.children)
        return rows1
      } else {
        delete rows1.children
      }

      return rows1
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>