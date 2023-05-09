<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="工号">
              <a-input placeholder="请输入工号" v-model="queryParam.jobnumber"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="门禁卡卡号">
              <a-input placeholder="请输入门禁卡卡号" v-model="queryParam.cardno"></a-input>
            </a-form-item>
          </a-col>

          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="类型">
                <a-select v-model="queryParam.type" placeholder="请选择类型">
                  <a-select-option value="1">员工</a-select-option>
                  <a-select-option value="2">外协</a-select-option>
                  <a-select-option value="3">临时</a-select-option>
                </a-select>
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
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>

      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importPhoto"
        @change="handleimportPhoto"
      >
        <a-button type="primary" icon="import">导入用户工号照片，JPG格式的zip压缩包</a-button>
      </a-upload>

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
        :scroll="{ x: 2000 }"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text,record">
          <a @click="handleEdit(record)" style="margin-right:10px;">编辑</a>
          <a @click="handleSyncFace(record)">同步人脸服务器</a>
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

        <span slot="portrait" slot-scope="text, record">
          <span>
            <img
              :src="imgprix+record.portrait"
              @click="open(imgprix+record.portrait)"
              style="height:60px;"
            />
          </span>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <comImgs ref="images" :urls="imgs" />
    <!-- 表单区域 -->
    <user-modal ref="modalForm" @ok="modalFormOk"></user-modal>
  </a-card>
</template>

<script>
import UserModal from './modules/UserModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { updatesysuserphoto } from '@/api/integratedapi'
import comImgs from './modules/imgsBig'

export default {
  name: 'UserList',
  mixins: [JeecgListMixin],
  components: {
    UserModal,
    comImgs
  },
  data() {
    return {
      description: '用户管理页面',
      imgprix: window._CONFIG['imgDomainURL'],
      imgs: '',
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
          title: '工号',
          align: 'center',
          dataIndex: 'jobnumber',
          width: 100
        },
        {
          title: '姓名',
          align: 'center',
          dataIndex: 'name',
          width: 100
        },
        {
          title: '性别',
          align: 'center',
          dataIndex: 'gender',
          width: 100,
          customRender: function(t, r, index) {
            var str = ''
            if (t == 1) {
              str = '男'
            } else if (t == 2) {
              str = '女'
            }
            return str
          }
        },
        {
          title: '手机',
          align: 'center',
          dataIndex: 'phone',
          width: 100
        },
        {
          title: '门禁卡卡号',
          align: 'center',
          dataIndex: 'cardno',
          width: 100
        },
        {
          title: '教育',
          align: 'center',
          dataIndex: 'education',
          width: 100
        },
        {
          title: '身份证',
          align: 'center',
          dataIndex: 'idcard',
          width: 100
        },
        {
          title: '类型',
          align: 'center',
          dataIndex: 'type',
          width: 100,
          customRender: function(t, r, index) {
            var str = ''
            if (t == 1) {
              str = '员工'
            } else if (t == 2) {
              str = '外协'
            } else if (t == 3) {
              str = '临时'
            }
            return str
          }
        },

        {
          title: '照片',
          align: 'center',
          dataIndex: 'portrait',
          width: 100,
          scopedSlots: { customRender: 'portrait' }
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'status',
          customRender: function(t, r, index) {
            var str = ''
            if (t == 1) {
              str = '在岗'
            } else if (t == 2) {
              str = '离职'
            }
            return str
          },
          width: 100
        },
        {
          title: '人脸库',
          align: 'center',
          dataIndex: 'stockname',
          width: 100
        },
        {
          title: '时间',
          align: 'center',
          dataIndex: 'createtime',
          width: 100
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
          width: 200
        }
      ],
      url: {
        list: '/integrated/user/list',
        delete: '/integrated/user/delete',
        deleteBatch: '/integrated/user/deleteBatch',
        exportXlsUrl: 'integrated/user/exportXls',
        importExcelUrl: 'integrated/user/importExcel',
        importPhoto: 'shipuser/importPhoto',
        sysuserphoto: 'shipuser/sysuserphoto'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
    importPhoto: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importPhoto}`
    },
    sysuserphoto: function() {
      return `${window._CONFIG['domianURL']}/${this.url.sysuserphoto}`
    }
  },
  methods: {
    handleimportPhoto(info) {
      if (info.file.status !== 'uploading') {
        // console.log(info.file, info.fileList);
      }
      if (info.file.response.successCount > 0) {
        this.$message.success(`文件上传成功`)
        this.loadData()
      } else if (info.file.status === 'error') {
        this.$message.error(`文件上传失败: ${info.file.msg} `)
      }
    },
    handleSyncFace(row) {
      updatesysuserphoto({ id: row.id }).then(resp => {
        this.$message.success(`成功`)
      })
    },
    open(url) {
      this.imgs = url
      this.$refs.images.watchPic()
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>