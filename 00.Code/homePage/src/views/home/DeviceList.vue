<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="设备名称">
              <a-input placeholder="设备名称" v-model="queryParam.userid"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="设备类型">
              <a-input placeholder="设备类型" v-model="queryParam.empCname"></a-input>
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
              <a-button
                type="primary"
                icon="download"
                @click="handleExportXls('1')"
                style="margin-left: 8px"
              >导出</a-button>
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
    <div class="table-operator" style="display:none;">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('1')">导出</a-button>
      <!-- <a-upload
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
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table ref="table" size="middle" bordered :columns="columns" :dataSource="dataSource">
        <span slot="action" slot-scope="text, record">
          <a @click="showchannellist(record)">通道列表</a>
        </span>
        <span slot="online_status" slot-scope="text, record">
           <span v-if="record.unit_type==1">在线</span>
           <span v-else>离线</span>
        </span>
        <span slot="category" slot-scope="text, record">
          <span v-if="record.category==1">编码器</span>
            <span v-if="record.category==3">解码器</span>
            <span v-if="record.category==5">报警主机</span>
            <span v-if="record.category==7">卡口</span>
            <span v-if="record.category==12">人脸门禁</span>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <a-modal title="通道列表" style="top: 20px;" @ok="f1" :width="1200" v-model="channelvisible">
      <a-table
        ref="table"
        size="middle"
        
        rowKey="id"
        :columns="channelcollum"
        :dataSource="dataSourcechannellist"
        pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="showvideoaddress(record)">流地址</a>
        </span>
        <span slot="unit_type" slot-scope="text, record">
           <span v-if="record.unit_type==1">视频输入通道</span>
           <span v-if="record.unit_type==2">解码通道</span>
           <span v-if="record.unit_type==3">报警输入通道</span>
           <span v-if="record.unit_type==4">报警输出通道</span>
           <span v-if="record.unit_type==7">卡口通道</span>
           <span v-if="record.unit_type==10">动环通道</span>
        </span>
        <span slot="camera_type" slot-scope="text, record">
           <span v-if="record.unit_type==1">枪机</span>
           <span v-if="record.unit_type==2">球机</span>
           <span v-if="record.unit_type==3">半球机</span>
        </span>
        <span slot="online_status" slot-scope="text, record">
           <span v-if="record.unit_type==1">在线</span>
           <span v-else>离线</span>
        </span>
      </a-table>
    </a-modal>

    <!-- 表单区域 -->
    <cardEvent-modal ref="modalForm" @ok="modalFormOk"></cardEvent-modal>

    <div class="test_two_box">
      <video id="myVideo" class="video-js">
        <source src="http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8" type="video/mp4" />
      </video>
    </div>
  </a-card>
</template>

<script>
// import CardEventModal from "./modules/CardEventModal";
import { JeecgListMixin } from "@/mixins/JeecgListMixin";
import { getchannellistapi,getcameraaddressapi } from "@/api/integratedapi";

export default {
  name: "CardEventList",
  mixins: [JeecgListMixin],
  // components: {
  //   CardEventModal
  // },
  data() {
    return {
      description: "1管理页面",
      channelvisible: false,
      // 表头
      columns: [
        {
          title: "#",
          dataIndex: "",
          key: "rowIndex",
          width: 60,
          align: "center",
          customRender: function(t, r, index) {
            return parseInt(index) + 1;
          }
        },

        {
          title: "设备名称",
          align: "center",
          dataIndex: "name"
        },

        {
          title: "设备类型",
          align: "center",
          dataIndex: "category",
          scopedSlots: { customRender: "category" }
        },
       
        {
          title: "ip地址",
          align: "center",
          dataIndex: "ip"
        },
        {
          title: "在线状态",
          align: "center",
          dataIndex: "online_status",
          scopedSlots: { customRender: "online_status" }
        },
        // {
        //   title: "经度",
        //   align: "center",
        //   dataIndex: "longitude"
        // },
        // {
        //   title: "纬度",
        //   align: "center",
        //   dataIndex: "latitude"
        // },
        {
          title: "通道列表",
          dataIndex: "action",
          align: "center",
          scopedSlots: { customRender: "action" }
        }
      ],
      dataSourcechannellist: [],
      channelcollum: [
        {
          title: "#",
          dataIndex: "",
          key: "rowIndex",
          width: 60,
          align: "center",
          customRender: function(t, r, index) {
            return parseInt(index) + 1;
          }
        },

        {
          title: "通道名称",
          align: "center",
          dataIndex: "name"
        },

        {
          title: "通道类型",
          align: "center",
          dataIndex: "unit_type",
          scopedSlots: { customRender: "unit_type" }
        },
        {
          title: "摄像机类型",
          align: "center",
          dataIndex: "camera_type",
          scopedSlots: { customRender: "camera_type" }
        },
        {
          title: "在线状态",
          align: "center",
          dataIndex: "online_status",
          scopedSlots: { customRender: "online_status" }
        },
        {
          title: "流地址",
          dataIndex: "action",
          align: "center",
          scopedSlots: { customRender: "action" }
        }
      ],
      url: {
        list: "/device/devicelist",
        delete: "/device/delete",
        deleteBatch: "/device/deleteBatch",
        exportXlsUrl: "device/exportXls",
        importExcelUrl: "device/importExcel"
      }
    };
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG["domianURL"]}/${this.url.importExcelUrl}`;
    }
  },
  methods: {
    showchannellist(row) {
      console.log(row.device_code);
      getchannellistapi({ device_code: row.device_code }).then(resp => {
        this.dataSourcechannellist = resp.result.records;
        var data = resp.result.records;
        
      });
      this.channelvisible = true;
    },
    showvideoaddress(row){
      
        getcameraaddressapi({ camera_id: row.camera_id }).then(resp => {
        var data = resp.result;
        this.$notification.success({
          message: '提示',
          description: `媒体流地址：`+data,
        });
      });
       
    },
    f1() {}
  }
};
</script>
<style scoped>
@import "~@assets/less/common.less";
</style>