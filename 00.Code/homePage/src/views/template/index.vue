<template>
  <div>
    <section class="tip-box">
      重要提示:当前系统有
      <span>30</span>份报价处理
      <span>10</span>份合同没有航次安排,
      <span>2</span>个航次估算需要处理。公司具有
      <span>3</span>艘新船入库,1艘船舶退租。
    </section>
    <section class="main-box">
      <div class="line-box">
        <a-tabs defaultActiveKey="1" @change="callback">
          <a-tab-pane tab="待办事项(待回复报价)" key="1">
            <div class="box">
              <a-table :columns="columns" :pagination="false" class="table-grey" :dataSource="data" :loading="loading" >
                <template slot="name" slot-scope="name">{{name.first}} {{name.last}}</template>
              </a-table>
            </div>
          </a-tab-pane>
          <a-tab-pane tab="货盘" key="2" forceRender>Content of Tab Pane 2</a-tab-pane>
          <a-tab-pane tab="船盘" key="3">Content of Tab Pane 3</a-tab-pane>
        </a-tabs>

        <a-tabs defaultActiveKey="1" @change="callback">
          <a-tab-pane tab="指数" key="1">
              <apexchart  class="chart" type="line" :options="chartOptions" :series="series"></apexchart>
          </a-tab-pane>
          <a-tab-pane tab="业务通告" key="2" forceRender>Content of Tab Pane 2</a-tab-pane>
        </a-tabs>
      </div>

       <div class="line-box">
        <section class="box-content">
            <div class="box-title">
              管理船舶
            </div>
            <div class="box-item">
              <a-table :columns="columns1" :pagination="false" class="table-grey" :dataSource="data1" :loading="loading" >
                <template slot="name" slot-scope="name">{{name.first}} {{name.last}}</template>
              </a-table>
            </div>
        </section>

        <a-tabs defaultActiveKey="1" @change="callback">
          <a-tab-pane tab="COA" key="1">
              <a-table :columns="columns2" :pagination="false" class="table-grey" :dataSource="data2" :loading="loading" >
                <template slot="name" slot-scope="name">{{name.first}} {{name.last}}</template>
              </a-table>
          </a-tab-pane>
          <a-tab-pane tab="待安排合同" key="2" forceRender>Content of Tab Pane 2</a-tab-pane>
        </a-tabs>
      </div>

      <div class="line-box">
       
         <div class="main-table">
            <div class="box-title">
              估算列表
            </div>
          <div class="table-content">
              <a-table :columns="columns3" :pagination="false" class="table-grey" :dataSource="data3" :loading="loading" >
                <template slot="name" slot-scope="name">{{name.first}} {{name.last}}</template>
              </a-table>
          </div>
         </div>
      </div>
    </section>
  </div>
</template>
<script>
const columns = [
  {
    title: '类型',
    dataIndex: 'name1'
  },
  {
    title: '摘要',
    dataIndex: 'name2',
     align:'center'
  },
  {
    title: '状态',
    dataIndex: 'name3',
    align:'center'
  }
]

const columns1 = [
  {
    title: '船舶',
    dataIndex: 'name1'
  },
  {
    title: '类型',
    dataIndex: 'name2',
     align:'center'
  },
  {
    title: 'TCE',
    dataIndex: 'name3',
    align:'center'
  },
  {
    title: '执行航次',
    dataIndex: 'name4',
    align:'center'
  },
    {
    title: '计划航次',
    dataIndex: 'name5',
    align:'center'
  }
]

const columns2 = [
  {
    title: '合同号',
    dataIndex: 'name1'
  },
  {
    title: '租家',
    dataIndex: 'name2',
     align:'center'
  },
  {
    title: '执行日期',
    dataIndex: 'name3',
    align:'center'
  },
  {
    title: '完成/待运',
    dataIndex: 'name4',
    align:'center'
  }
]

const columns3 = [
  {
    title: '估算号',
    dataIndex: 'name1'
  },
  {
    title: '船舶',
    dataIndex: 'name2',
     align:'center'
  },
  {
    title: '航线',
    dataIndex: 'name3',
    align:'center'
  },
  {
    title: '货量',
    dataIndex: 'name4',
    align:'center'
  },
   {
    title: '收入',
    dataIndex: 'name5',
    align:'center'
  },
   {
    title: '成本',
    dataIndex: 'name6',
    align:'center'
  },
   {
    title: '航天',
    dataIndex: 'name7',
    align:'center'
  },
   {
    title: 'TCE',
    dataIndex: 'name8',
    align:'center'
  },
   {
    title: '状态',
    dataIndex: 'name9',
    align:'center'
  }
]
export default {
  data() {
    return {
      columns,
       chartOptions: {
          title: {
            text: '罗的海BD指数【BD】2019-10-05',
            align: 'left'
          },
          chart: {
            id: 'vuechart-example'
          },
          xaxis: {
            categories: ['7-17', "7-18", "7-19", "7-20", "7-21", "7-22", "7-23", "7-24"]
          },
            legend: {
            position: 'top',
            horizontalAlign: 'right',
            floating: true,
            offsetY: -25,
            offsetX: -5
          }
        },
       
        series: [{
          name: 'series-1',
          data: [30, 40, 35, 50, 49, 69, 70, 80]
        },
        {
          name: 'series-2',
          data: [30, 20, 32, 40, 44, 66, 30, 80]
        },
        {
          name: 'series-3',
          data: [10, 22, 37, 45, 49, 30, 20, 75]
        }
        ],
        columns1,
        columns2,
        columns3,
        data1:[
          {
            name1:'华山',
            name2:'租入',
            name3:'1230',
            name4:'VC-1902',
            name5:'C-1903'
          },
           {
            name1:'华山',
            name2:'租入',
            name3:'1230',
            name4:'VC-1902',
            name5:'C-1903'
          },
           {
            name1:'华山',
            name2:'租入',
            name3:'1230',
            name4:'VC-1902',
            name5:'C-1903'
          },
           {
            name1:'华山',
            name2:'租入',
            name3:'1230',
            name4:'VC-1902',
            name5:'C-1903'
          },
           {
            name1:'华山',
            name2:'租入',
            name3:'1230',
            name4:'VC-1902',
            name5:'C-1903'
          },
           {
            name1:'华山',
            name2:'租入',
            name3:'1230',
            name4:'VC-1902',
            name5:'C-1903'
          },
           {
            name1:'华山',
            name2:'租入',
            name3:'1230',
            name4:'VC-1902',
            name5:'C-1903'
          }

        ],
        data2:[
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'1230/v1902',
            name4:'10k/5k'
          },
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'1230/v1902',
            name4:'10k/5k'
          },
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'1230/v1902',
            name4:'10k/5k'
          },
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'1230/v1902',
            name4:'10k/5k'
          },
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'1230/v1902',
            name4:'10k/5k'
          },
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'1230/v1902',
            name4:'10k/5k'
          },
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'1230/v1902',
            name4:'10k/5k'
          }
        ],
        data3:[
          {
            name1:'v140911',
            name2:'宝钢',
            name3:'SHA-A.K',
            name4:'10k/5k',
            name5:'$12345k',
            name6:'$123.66k',
            name7:'12.3',
            name8:'$15566',
            name9:'新建'
          },
           {
            name1:'v140911',
            name2:'宝钢',
            name3:'SHA-A.K',
            name4:'10k/5k',
            name5:'$12345k',
            name6:'$123.66k',
            name7:'12.3',
            name8:'$15566',
            name9:'新建'
          },
           {
            name1:'v140911',
            name2:'宝钢',
            name3:'SHA-A.K',
            name4:'10k/5k',
            name5:'$12345k',
            name6:'$123.66k',
            name7:'12.3',
            name8:'$15566',
            name9:'新建'
          },
           {
            name1:'v140911',
            name2:'宝钢',
            name3:'SHA-A.K',
            name4:'10k/5k',
            name5:'$12345k',
            name6:'$123.66k',
            name7:'12.3',
            name8:'$15566',
            name9:'新建'
          },
           {
            name1:'v140911',
            name2:'宝钢',
            name3:'SHA-A.K',
            name4:'10k/5k',
            name5:'$12345k',
            name6:'$123.66k',
            name7:'12.3',
            name8:'$15566',
            name9:'新建'
          },
           {
            name1:'v140911',
            name2:'宝钢',
            name3:'SHA-A.K',
            name4:'10k/5k',
            name5:'$12345k',
            name6:'$123.66k',
            name7:'12.3',
            name8:'$15566',
            name9:'新建'
          }
        ],
      data: [
        {
          name1: '合同',
          name2: '',
          name3: ''
        },
        {
          name1: '合同',
          name2: '',
          name3: ''
        },
        {
          name1: '合同',
          name2: '',
          name3: ''
        },
        {
          name1: '合同',
          name2: '',
          name3: ''
        },
        {
          name1: '合同',
          name2: '',
          name3: ''
        },
        {
          name1: '合同',
          name2: '',
          name3: ''
        },
        {
          name1: '合同',
          name2: '',
          name3: ''
        }
      ],
      loading: false
    }
  }
}
</script>
<style lang="less">
.tip-box {
  height: 70px;
  background: #fff;
  line-height: 70px;
  padding-left: 20px;
  font-size: 15px;
  color: 000;
  span {
    color: #357eff;
    margin: 0 2px;
    font-weight: 600;
    font-size: 14px;
  }
}

.main-box {
  margin-top: 14px;
  .line-box:first-child{
    .ant-tabs:nth-of-type(odd) {
      margin-right: 10px;
    }
  }
  .line-box {
    display: flex;
    margin-bottom:12px;
    .box-title{
         color:#000;
         padding-left:20px;
        //  height:50px;
         line-height: 47px;
         border-bottom: 1px solid #e8e8e8;
       }
    .box-content{
      flex:1;
      background: #fff;
       margin-right: 10px;
       
    }
    .ant-tabs {
      flex: 1;
      background: #fff;
    }
    
  }
}
// .box{
//     padding:30px;
//     padding-top:0;
// }
.table-grey{
  padding:0 20px;
  th{
    background:#fafafa!important;
  }

}
.table-content{
  padding:15px 0;
}
.main-table{
  flex:1;
  background: #fff;
}

.box-item{
  padding:15px 0;
  }
  .chart{
    width:100%;
  }
</style>