<template>
   <a-card id="content" :style="{height:height + 'px'}">
      <!-- <img src="../../assets/bj.jpeg" alt=""> -->
      <a-row>
      <a-col :span="6">
        <div class="info-box">
          <h3>出勤</h3>
          <p>200</p>
        </div>
      </a-col>
      <a-col :span="6">
        <div  class="info-box">
          <h3>请假</h3>
          <p>10</p>
        </div>
      </a-col>
       <a-col :span="6">
        <div  class="info-box">
          <h3>迟到</h3>
          <p>32</p>
        </div>
      </a-col>
       <a-col :span="6">
        <div  class="info-box">
          <h3>缺卡</h3>
          <p>5</p>
        </div>
      </a-col>
    </a-row>
      <a-row>
         <a-col :span="12">
          <div class="bar-box" id="barchar"></div>
        </a-col>
          <a-col :span="12">
          <div class="bar-box" id='barchar1'></div>
        </a-col>
      </a-row>
    </a-card>
</template>

<script>
import echarts from 'echarts'
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'

  import Trend from '@/components/Trend'
  import { getLoginfo,getVisitInfo } from '@/api/api'

  const rankList = []
  for (let i = 0; i < 7; i++) {
    rankList.push({
      name: '白鹭岛 ' + (i+1) + ' 号店',
      total: 1234.56 - i * 100
    })
  }
  const barData = []
  for (let i = 0; i < 12; i += 1) {
    barData.push({
      x: `${i + 1}月`,
      y: Math.floor(Math.random() * 1000) + 200
    })
  }
  export default {
    name: "Analysis",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        height:0,
        loading: true,
        center: null,
        rankList,
        barData,
        loginfo:{},
        visitFields:['ip','visit'],
        visitInfo:[],
        indicator: <a-icon type="loading" style="font-size: 24px" spin />
      }
    },
    created() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.initLogInfo();
    },
    mounted(){
     
     
      this.height=document.body.clientHeight-111;
    
    this.$nextTick(()=>{
         let myChart =echarts.init(document.getElementById('barchar'))
        myChart.setOption({
    title: {
        text: '出勤情况汇总',
        // subtext: '纯属虚构',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
        orient: 'vertical',
        left: 'right',
        data: ['正常', '请假', '迟到', '缺卡']
    },
    series: [
        {
            name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
                {value: 200, name: '正常'},
                {value: 10, name: '请假'},
                {value:32, name: '迟到'},
                {value: 5, name: '缺卡'}
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});
       let mychart1=echarts.init(document.getElementById('barchar1'));
      var options1={
     title: {
        text: '出勤情况分布统计',
        left: 'center'
    },
   legend: {
        orient: 'vertical',
        left: 'right'
    },
    tooltip: {},
    dataset: {
        source: [
            ['product', '正常', '迟到', '早退','缺卡'],
            ['指挥中心', 43, 3, 1,2],
            ['信息中心', 79, 5, 3,1],
            ['狱政科', 78, 4, 1,1]
        ]
    },
    xAxis: {type: 'category'},
    yAxis: {},
    // Declare several bar series, each will be mapped
    // to a column of dataset.source by default.
    series: [
        {type: 'bar'},
        {type: 'bar'},
        {type: 'bar'},
         {type: 'bar'},
    ]
};

      mychart1.setOption(options1);
    })
   
    },
    methods: {
      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            Object.keys(res.result).forEach(key=>{
              res.result[key] =res.result[key]+""
            })
            this.loginfo = res.result;
          }
        })
        getVisitInfo().then(res=>{
          if(res.success){
            //  console.log("aaaaaa",res.result)
             this.visitInfo = res.result;
           }
         })
      },
    }
  }
</script>

<style lang="scss" scoped>

.info-box{
  width:200px;
  height:160px;
   box-shadow: 0px 0px 7px #cdcdcd;
  box-sizing: border-box;
  padding:10px;
  margin: 0 auto;
  // background: red;
  border-radius: 8px;
   display: flex;
   flex-direction: column;
   h3{
      margin:0;
      color:#1890ff;
   }
  p{
  flex:1;
  display: flex;
  justify-content: center;
  align-items: center;
  margin:0;
  font-size: 40px;
  }
}

.bar-box{
  width:480px;

  margin:0 auto;
  height:300px;
  margin-top:80px;
}
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
 img{
   width:100%;
   height:100%;
 }
</style>