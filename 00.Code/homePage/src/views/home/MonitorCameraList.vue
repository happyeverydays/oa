<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="所在区域">
              <j-dict-select-tag
                v-model="queryParam.areaid"
                placeholder="请选择所在区域"
                dictCode="base_area,location,id"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="监控名称">
              <a-input placeholder="请输入监控名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>

          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="通道号">
                <a-input placeholder="请输入通道号" v-model="queryParam.distchannel"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="地址">
                <a-input placeholder="请输入地址" v-model="queryParam.url"></a-input>
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
      <a-button @click="showBigScreen" style="display:none" type="primary" icon="plus">大屏录像位置</a-button>
      <!-- <a-button @click="showBigScreenList" type="primary" icon="pic-left">大屏列表</a-button> -->
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('监控')">导出</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <!-- <a-button type="primary" icon="import">导入</a-button> -->
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <!-- <a-menu-item key="1" @click="batchaddbigcreen">
            <a-icon type="plus" />添加大屏列表
          </a-menu-item>-->
          <a-menu-item key="2" @click="batchDel">
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
        <span scroll="{ x: 1600 }" slot="action" slot-scope="text, record">
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
    <monitorCamera-modal ref="modalForm" @ok="modalFormOk"></monitorCamera-modal>

    <a-modal
      title="大屏流地址"
      style="top: 20px;"
      @ok="saveflowadddress"
      :width="800"
      v-model="isshowBigScreen"
    >
      <a-form layout="inline">
        <a-row>
          <a-form-item label>
            <a-textarea placeholder="请输入大屏流地址" v-model="flowaddress" style="width:600px;"></a-textarea>
          </a-form-item>
        </a-row>
      </a-form>
    </a-modal>

    <!-- <a-modal
      title="大屏播放间隔"
      style="top: 20px;"
      @ok="saveBigScreenminutes"
      :width="800"
      v-model="isshowBigScreenminutes"
    >
      <a-form layout="inline">
        <a-row>
          <a-form-item label="时间(分钟)">
            <a-input placeholder="请输入大屏播放间隔" v-model="flowminutes"></a-input>
          </a-form-item>
         
        </a-row>
      </a-form>
    </a-modal>-->

    <a-modal
      title="大屏播放列表"
      style="top: 20px;"
      @ok="hideBigScreenList"
      :width="800"
      v-model="isshowBigScreenList"
    >
      <a-form layout="inline">
        <a-row>
          <a-form-item label="大屏播放间隔(分钟)">
            <a-input placeholder="请输入大屏播放间隔" v-model="flowminutes"></a-input>
          </a-form-item>

          <a-form-item label>
            <a-button type="primary" @click="saveBigScreenminutes" icon>确定</a-button>
          </a-form-item>
        </a-row>
      </a-form>

      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns1"
        :dataSource="dataSource1"
        :pagination="ipagination1"
        @change="handleTableChange1"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handledelbigscreen(record)" style="color:red;">删除</a>
        </span>
      </a-table>
    </a-modal>
  </a-card>
</template>

<script>
import MonitorCameraModal from './modules/MonitorCameraModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import {
  getflowadddressapi,
  updatebigscreenaddressapi,
  addalladdresslistapi,
  getvideoaddressminutesapi,
  updatevideoaddressminutesapi
} from '@/api/integratedapi'
import { deleteAction, getAction, downFile } from '@/api/manage'
import { getchannellistapi, getcameraaddressapi } from '@/api/integratedapi'
import { initDictOptions, filterDictText } from '@/components/dict/JDictSelectUtil'

export default {
  name: 'MonitorCameraList',
  mixins: [JeecgListMixin],
  components: {
    MonitorCameraModal
  },
  data() {
    return {
      isshowBigScreenList: false,
      dataSource1: [],
      ipagination1: { current: 1, total: 0 },
      isshowBigScreenminutes: false,
      flowaddress: '',
      isshowBigScreen: false,
      description: '监控管理页面',
      flowminutes: 0,
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
          title: '所在区域',
          align: 'center',
          dataIndex: 'areaname'
        },

        {
          title: '监控名称',
          align: 'center',
          dataIndex: 'name'
        },
        {
          title: '通道号',
          align: 'center',
          dataIndex: 'distchannel'
        },

        {
          title: '流地址',
          align: 'center',
          dataIndex: 'url',
          width: 600
        },
        {
          title: 'ip地址',
          align: 'center',
          dataIndex: 'ipaddress'
        },
        {
          title: '用户名',
          align: 'center',
          dataIndex: 'username'
        },
        {
          title: '密码',
          align: 'center',
          dataIndex: 'password'
        },
        {
          title: '端口号',
          align: 'center',
          dataIndex: 'port'
        },
        {
          title: '操作',
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
          title: '监控名称',
          align: 'center',
          dataIndex: 'videoname'
        },
        {
          title: '通道号',
          align: 'center',
          dataIndex: 'channelid'
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
        list: 'monitorCamera/list',
        delete: 'monitorCamera/delete',
        deleteBatch: 'monitorCamera/deleteBatch',
        exportXlsUrl: 'monitorCamera/exportXls',
        importExcelUrl: 'monitorCamera/importExcel',
        getalladdresslisturl: 'bigscreen/getalladdresslist',
        delbigscreenurl: 'bigscreen/delbigscreen',
        delbigscreenByid: 'bigscreen/delbigscreenByid'
      }
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  created() {
    // getflowadddressapi().then(res => {
    //   this.flowaddress = res.result
    // })
    // getvideoaddressminutesapi().then(res => {
    //   this.flowminutes = res.result
    // })
  },
  methods: {
    handleTableChange1(data) {
      this.ipagination1.current = data.current
    },
    loadData1(arg) {
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination1.current = 1
      }
      getAction(this.url.getalladdresslisturl, {}).then(res => {
        if (res.success) {
          this.dataSource1 = res.result
          this.ipagination1.total = res.result.length
        }
      })
    },
    showvideoaddress(row) {
      getcameraaddressapi({ camera_id: row.channelidname }).then(resp => {
        var data = resp.result
        this.$notification.success({
          message: '提示',
          description: `媒体流地址：` + data
        })
      })
    },
    handledelbigscreen(record) {
      getAction(this.url.delbigscreenByid + '?id=' + record.id, {}).then(res => {
        if (res.success) {
          this.loadData1(1)
        }
      })
    },
    hideBigScreenList() {
      this.isshowBigScreenList = false
    },
    showBigScreenList() {
      this.loadData1(1)
      this.isshowBigScreenList = true
    },
    showBigScreenminutes() {
      this.isshowBigScreenminutes = true
    },
    saveBigScreenminutes() {
      updatevideoaddressminutesapi('?minute=' + this.flowminutes)
      this.$notification.success({
        message: '提示',
        description: `保存成功`
      })
      this.isshowBigScreenminutes = false
    },
    batchaddbigcreen() {
      var ids = ''
      for (var a = 0; a < this.selectedRowKeys.length; a++) {
        ids += this.selectedRowKeys[a] + ','
      }
      if (ids.length > 0) {
        ids = ids.substring(0, ids.length - 1)
      }

      addalladdresslistapi('?ids=' + ids).then(resp => {})
      this.$notification.success({
        message: '提示',
        description: `保存成功`
      })
    },
    beforeLoad() {
      this.queryParam.companyId = this.getcompanyid()
    },
    showBigScreen() {
      this.isshowBigScreen = true
    },
    saveflowadddress() {
      updatebigscreenaddressapi({ address: this.flowaddress }).then(resp => {
        this.$notification.success({
          message: '提示',
          description: `保存成功`
        })
        this.isshowBigScreen = false
      })
    },
    initDictConfig() {
      initDictOptions('sys_depart,depart_name,id').then(res => {
        if (res.success) {
          this.sys_departDictOptions = res.result
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>