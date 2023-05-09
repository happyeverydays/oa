<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="名称">
              <a-input placeholder="请输入名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="激活">
              <a-input placeholder="请输入rotateActive" v-model="queryParam.rotateActive"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="X角度">
                <a-input placeholder="请输入angleX" v-model="queryParam.angleX"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="Y角度">
                <a-input placeholder="请输入angleY" v-model="queryParam.angleY"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="Z角度">
                <a-input placeholder="请输入angleZ" v-model="queryParam.angleZ"></a-input>
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
      <a-button @click="model3dAdd" type="primary" icon="plus">新增3D</a-button>
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('三维管理')">导出</a-button>
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
          <a @click="show3dmodel(record)" style="margin-right:5px;">查看</a>
          <a @click="edit3dmodel(record)" style="margin-right:5px;">编辑</a>
          <a @click="handleEdit(record)">修改</a>

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
        <span slot="objFileSlot" slot-scope="text, record">
          <a v-if="text" :href="text" style="margin-right:5px;">模型</a>
          <span v-else></span>
        </span>
        <span slot="backImageSlot" slot-scope="text, record">
          <img v-if="text" :src="text" style="width:50p;height:50px;" />
        </span>
        <span slot="mtlSlot" slot-scope="text, record">
          <a v-if="text" :href="text" style="margin-right:5px;">mtl</a>
          <span v-else></span>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <objModel-modal ref="modalForm" @ok="modalFormOk"></objModel-modal>

    <a-modal
      title="3纬"
      style="top: 20px;"
      @ok="confirm3dmodel"
      :width="1200"
      v-model="isshow3dmodel"
    >
      <iframe :src="show3dmodelurl" style="width:100%;min-height:700px;border:0;"></iframe>
    </a-modal>
  </a-card>
</template>

<script>
import ObjModelModal from './modules/ObjModelModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'

export default {
  name: 'ObjModelList',
  mixins: [JeecgListMixin],
  components: {
    ObjModelModal
  },
  data() {
    return {
      description: '三维管理管理页面',
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
          title: 'id',
          align: 'center',
          dataIndex: 'id'
        },
        {
          title: '名称',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '模型',
          align: 'center',
          dataIndex: 'objFileStr',
          scopedSlots: { customRender: 'objFileSlot' }
        },
        {
          title: '移动',
          align: 'center',
          dataIndex: 'isMovable'
        },
        // {
        //   title: '方位路径',
        //   align: 'center',
        //   dataIndex: 'path'
        // },
        {
          title: '背景图',
          align: 'center',
          dataIndex: 'backgroundImgStr',
          scopedSlots: { customRender: 'backImageSlot' }
        },

        {
          title: '背景颜色',
          align: 'center',
          dataIndex: 'backgroundColor'
        },
        {
          title: 'mtl',
          align: 'center',
          dataIndex: 'mtlUrl',
          scopedSlots: { customRender: 'mtlSlot' }
        },

        // {
        //   title: '激活',
        //   align: 'center',
        //   dataIndex: 'rotateActive'
        // },
        {
          title: 'X角度',
          align: 'center',
          dataIndex: 'angleX',
          customRender: item => {
            if (item) {
              return item.toFixed(4)
            }
          }
        },
        {
          title: 'Y角度',
          align: 'center',
          dataIndex: 'angleY',
          customRender: item => {
            if (item) {
              return item.toFixed(4)
            }
          }
        },
        {
          title: 'Z角度',
          align: 'center',
          dataIndex: 'angleZ',
          customRender: item => {
            if (item) {
              return item.toFixed(4)
            }
          }
        },

        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/objModel/list',
        delete: '/objModel/delete',
        deleteBatch: '/objModel/deleteBatch',
        exportXlsUrl: 'objModel/exportXls',
        importExcelUrl: 'objModel/importExcel',
        model3durl: 'http://218.92.85.58:31008/upload/stock/three/'
        //  http://file.fangbb.vip/three/detail.html?objId=45
      },
      isshow3dmodel: false,
      show3dmodelurl: ''
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    show3dmodel(record) {
      this.isshow3dmodel = true
      this.show3dmodelurl = this.url.model3durl + 'detail.html?objId=' + record.id
    },
    edit3dmodel(record) {
      this.isshow3dmodel = true
      this.show3dmodelurl = this.url.model3durl + 'obj-manage.html?objId=' + record.id
    },
    model3dAdd() {
      this.isshow3dmodel = true
      this.show3dmodelurl = ''
      this.show3dmodelurl = this.url.model3durl + 'obj-manage.html'
    },
    confirm3dmodel() {
      this.isshow3dmodel = false
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>