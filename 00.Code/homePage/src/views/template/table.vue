<template>
  <div class="template-main">
    <!-- 筛选区域 -->
    <transition name="slide-fadeone">
    <div class="info-box tep-border" v-if="!visible">
      <div class="title-box">数据筛选</div>
      <section class="info-order">
        <div class="small-title">类型</div>
        <a-checkbox-group @change="onChange">
          <a-row :span="24">
            <a-col
              :span="12"
              :class="[(index+1)%2==0?'textRight':'']"
              v-for="(item,index) in listInfo"
              :key="index"
            >
              <a-checkbox :value="item.value" :class="[(index+1)%2==0?'textLeft':'']">{{item.label}}</a-checkbox>
            </a-col>
          </a-row>
        </a-checkbox-group>

        <div class="small-title">单据</div>
        <a-checkbox-group>
          <a-row :span="24">
            <a-col :span="12">
              <a-checkbox :value="1">我的单据</a-checkbox>
            </a-col>
          </a-row>
        </a-checkbox-group>

        <div class="small-title">船舶</div>
        <a-select
          size="large"
          placeholder="请选择"
          style="width:100%"
          defaultValue="a1"
          @change="handleChange"
        >
          <a-select-option
            v-for="i in 25"
            :key="(i + 9).toString(36) + i"
          >{{(i + 9).toString(36) + i}}</a-select-option>
        </a-select>
        <div class="small-title">合同号</div>
        <a-input size="large" placeholder="请输入" />

        <div class="btn-bottom">
          <a-button block>重置</a-button>
          <a-button type="primary" block>搜索</a-button>
        </div>
      </section>
    </div>
       </transition>
    <!-- 筛选区域 -->

    <!-- 表格展示 -->

    <a-tabs defaultActiveKey="3" class="table-box tep-border">
      <a-tab-pane key="1">
        <span slot="tab">
          <a-icon type="filter" />筛选
        </span>
        Tab 1
      </a-tab-pane>
      <a-tab-pane key="2">
        <span slot="tab">
          <a-icon type="file-text" />主题
        </span>
        <section class="detail-box">
          <div class="line-item" v-for="(item,index) in itemlist" :key="index" :class="[itemIndex=== index?'item-active':'']" @click="choseitem(index)">
            <div class="line-title">coa-121212121</div>
            <ul>
              <li>版本:1.0</li>

              <li>合同状态:草拟</li>

              <li>审批状态:待处理</li>

              <li>批次:0/1</li>

              <li>批次货物总量:70500/80000</li>

              <li>租家:上海海华货运</li>

              <li>船东:上海长航国际海运有限公司</li>

              <li>摘要:上海海华货运/COA/煤炭/200.000</li>

              <li>管理分类:国际航线</li>

              <li>风险等级:中</li>

              <li>用金模式:费率</li>

              <li>圣纪佣金率(%):5.4</li>
            </ul>
          </div>
         
        </section>
      </a-tab-pane>
      <a-tab-pane key="3">
        <span slot="tab">
          <a-icon type="copy" />列表
        </span>
        <div>
          <div class="title-table">外贸列表</div>
          <a-table :columns="columns" :dataSource="data" @change="onChange" />
        </div>
      </a-tab-pane>
      <div
        style="position:relative;"
        slot="tabBarExtraContent"
        @mouseleave="hideselect"
        @mouseenter="showselect"
      >
        <transition>
          <div class="chose-box" v-if="show">
            <span v-for="(item,index) in listInfo" :key="index">{{item.label}}</span>
          </div>
        </transition>
        <div class="btn-edit">
          <a-icon :type="show?'menu-unfold':'menu-fold'" />操作
        </div>
      </div>
    </a-tabs>

    <!-- 表格展示 -->
    
    <!-- 详情展示 -->
    <transition name="slide-fade">

    <div class="detail-content" :class="[showborder?'active':'']" v-if="visible">
      <div class="title-box">COA-外贸 编辑</div>
      <a-tabs defaultActiveKey="1" @change="callback">
        <a-tab-pane tab="合同基本信息" key="1">
          <div class="small-title">基本信息</div>
          <section class="form-content">
            <div class="input-item" v-for="item in list">
              <div class="label-box">{{item.label}}</div>
              <!-- {{item.type=='select'}} -->
              <template v-if="item.type=='select'">
                <a-select
                  showSearch
                  placeholder="Select a person"
                  optionFilterProp="children"
                  style="width: 200px"
                  size="small"
                  @focus="handleFocus"
                  @blur="handleBlur"
                  @change="handleChange"
                  :filterOption="filterOption"
                >
                  <a-select-option value="jack">Jack</a-select-option>
                  <a-select-option value="lucy">Lucy</a-select-option>
                  <a-select-option value="tom">Tom</a-select-option>
                </a-select>
              </template>
              <template v-else>
                <a-input size="small" placeholder="请输入" />
              </template>
            </div>
          </section>
          <div class="small-title">附件</div>
          <section class="add-file">
            <a-checkbox @change="onChange">正本合同</a-checkbox>
            <div>
              <a-button type="primary">合同附件</a-button>附件
              <span class="num">0</span>个
            </div>
            <div>
              <a-button type="primary">决策支持</a-button>附件
              <span class="num">0</span>个
            </div>
          </section>

          <div class="small-title">货量计划</div>
          <a-table
            :columns="columns"
            style="padding-left:40px;"
            :pagination="false"
            :dataSource="data"
            @change="onChange"
          />

          <div class="buttom-box">
            <a-button type="primary" size="large" width block>提交</a-button>
          </div>
        </a-tab-pane>
        <a-tab-pane tab="附件" key="2" forceRender>Content of Tab Pane 2</a-tab-pane>
        <a-tab-pane tab="多港多费率" key="3">Content of Tab Pane 3</a-tab-pane>
        <a-tab-pane tab="货量计划" key="4">Content of Tab Pane 3</a-tab-pane>
        <a-tab-pane tab="港口航区" key="5">Content of Tab Pane 3</a-tab-pane>
        <a-tab-pane tab="燃油条款" key="6">Content of Tab Pane 3</a-tab-pane>
        <a-tab-pane tab="船期要求" key="7">Content of Tab Pane 3</a-tab-pane>
        <a-tab-pane tab="限定船铂" key="8">Content of Tab Pane 3</a-tab-pane>
      </a-tabs>


      <div class="up-btn" @mouseenter="showborder=true"  @mouseleave="showborder=false" @click="closemodel">
        收起<a-icon type="right-circle" />
      </div>
    </div>
     </transition>
        <!-- 详情展示 -->
  </div>
</template>

<script>
const columns = [
  {
    title: 'Name',
    dataIndex: 'name',
    filters: [
      {
        text: 'Joe',
        value: 'Joe'
      },
      {
        text: 'Jim',
        value: 'Jim'
      },
      {
        text: 'Submenu',
        value: 'Submenu',
        children: [
          {
            text: 'Green',
            value: 'Green'
          },
          {
            text: 'Black',
            value: 'Black'
          }
        ]
      }
    ],
    // specify the condition of filtering result
    // here is that finding the name started with `value`
    onFilter: (value, record) => record.name.indexOf(value) === 0,
    sorter: (a, b) => a.name.length - b.name.length,
    sortDirections: ['descend']
  },
  {
    title: 'Age',
    dataIndex: 'age',
    sorter: (a, b) => a.age - b.age
  },
  {
    title: 'Address',
    dataIndex: 'address',
    filters: [
      {
        text: 'London',
        value: 'London'
      },
      {
        text: 'New York',
        value: 'New York'
      }
    ],
    filterMultiple: false,
    onFilter: (value, record) => record.address.indexOf(value) === 0,
    sorter: (a, b) => a.address.length - b.address.length,
    sortDirections: ['descend', 'ascend']
  }
]

const data = [
  {
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park'
  },
  {
    key: '2',
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park'
  },
  {
    key: '3',
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park'
  },
  {
    key: '4',
    name: 'Jim Red',
    age: 32,
    address: 'London No. 2 Lake Park'
  }
]

function onChange(pagination, filters, sorter) {
  console.log('params', pagination, filters, sorter)
}
export default {
  data() {
    return {
      itemIndex:'',
      itemlist:[1,2,3,4,5],
      list: [
        {
          type: 'select',
          label: '合同种类:'
        },
        {
          label: '合同号:'
        },
        {
          label: '摘要:'
        },
        {
          label: '船东:'
        },
        {
          label: '租家:'
        },
        {
          label: '操作人:'
        },
        {
          label: '操作部门:'
        },
        {
          label: '管理分类:'
        },
        {
          label: '风险等级:'
        },
        {
          label: '摘要:'
        }
      ],

      data,
      columns,
      visible: false,
      show: false,
      
      showborder:false,
      listInfo: [
        {
          value: 0,
          label: '草拟'
        },
        {
          value: 1,
          label: '审批中'
        },
        {
          value: 2,
          label: '审批通过'
        },
        {
          value: 3,
          label: '执行'
        },
        {
          value: 4,
          label: '更新中'
        },
        {
          value: 5,
          label: '已清账'
        },
        {
          value: 6,
          label: '已取消'
        },
        {
          value: 7,
          label: '作废'
        },
        {
          value: 8,
          label: '已拆分'
        },
        {
          value: 9,
          label: '所有'
        }
      ]
    }
  },
  methods: {
    choseitem(index){
      this.itemIndex=index;
      this.visible=true;
    },
    closemodel(){
      this.visible=false;
      this.itemIndex='';
    },
    showselect() {
      this.show = true
    },
    hideselect() {
      this.show = false
    },
    showDrawer() {
      this.visible = true
    },
    onClose() {
      // alert()
      this.visible = false
    },
    onChange(e) {
      this.placement = e.target.value
    }
  }
}
</script>

<style lang="less">
.tep-border {
  border-radius: 4px;
}
.template-main {
  display: flex;
  .info-box {
    width: 325px;
    // height:400px;
    background: #fff;
  }
  .table-box {
    width: 500px;
    background: #fff;
    flex: 1;
    margin-left: 12px;
  }
}

.title-box {
  height: 48px;
  line-height: 48px;
  font-size: 16px;
  color: #000;
  padding: 0 20px;
  border-bottom: 1px solid #e7e7e7;
}

.info-order {
  padding: 0 20px;
  padding-bottom: 100px;
  .ant-checkbox-group {
    width: 100%;
  }
  .ant-col-12 {
    margin-bottom: 10px;
  }
}
.textRight {
  text-align: right;
}
.textLeft {
  width: 75px;
  text-align: left;
}
.small-title {
  margin-top: 25px;
  margin-bottom: 15px;
  color: #000;
  font-size: 14px;
  font-weight: 600;
  padding-left: 40px;
}

.btn-bottom {
  display: flex;
  margin-top: 65px;
  justify-content: space-between;
  .ant-btn-primary {
    margin-left: 20px;
  }
}

.title-table {
  padding: 20px;
  font-size: 14px;
  color: #000;
  font-weight: 600;
}

.table-grey {
  padding: 0 20px;
  th {
    background: #fafafa !important;
  }
}

// .table-box{

// }
.btn-edit {
  cursor: pointer;
  width: 90px;
  height: 48px;
  margin-top: -4px;
  background: #357eff;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  .anticon-menu-fold,
  .anticon-menu-unfold {
    margin-right: 4px;
  }
}

.chose-box {
  position: absolute;
  z-index: 1000;
  right: 90px;
  // top:-4px;
  width: 690px;
  height: 48px;
  line-height: 48px;
  background: #e1e4eb;
  cursor: pointer;
  overflow: hidden;
  span {
    padding: 0 15px;
  }
}

/* v-enter [这是一个时间点] 进入之前元素的起始状态，此时还没有开始进入*/
/* v-leave-to [] 动画离开之后 离开的终止状态 此时，元素动画已经结束了*/
.v-enter,
.v-leave-to {
  width: 0;
}
/*v-enter-active 入场动画的时间段*/
/* v-leave-active 出场动画的时间段*/
.v-enter-active,
.v-leave-active {
  transition: all .08s ease;
}
.line-title {
  padding-left: 15px;
  margin-bottom: 15px;
}
.detail-box {
  width: 100%;
  ul,
  li {
    list-style-type: none;
    margin: 0;
    padding: 0;
  }
  ul {
    padding: 0 15px;
    display: flex;
    flex-wrap: wrap;

    li {
      width: 33.3%;
      margin-bottom: 10px;
    }
  }
  .line-item {
    border-bottom: 1px solid #d9d9d9;
    padding-bottom: 15px;
    min-width:900px;
  }
}
.form-content {
  padding-left: 40px;
  display: flex;
  flex-wrap: wrap;
  .input-item {
    .label-box {
      width: 90px;
    }
    .ant-input {
      border: 0;
      border-radius: 0;
      border-bottom: 1px solid #d9d9d9;
    }
    margin-bottom: 20px;
    display: flex;
    width: 25%;
    padding-right: 50px;
  }
}

.ant-select-selection {
  border: 0;
  border-radius: 0;
  border-bottom: 1px solid #d9d9d9;
}

.add-file {
  padding-left: 40px;
  display: flex;
  align-items: center;
  div {
    margin: 0 30px;
    .ant-btn {
      margin-right: 10px;
    }
    .num {
      margin: 0 5px;
      color: #357eff;
    }
  }
}
.buttom-box {
  // display: flex;
  // justify-content:center;
  padding-top: 30px;
  padding-left: 40px;
  .ant-btn {
    width: 160px;
  }
}

.detail-content {
  position: relative;
  .up-btn{
    cursor: pointer;
    position: absolute;
    width:25px;
    text-align: center;
    // height:80px;
    // position: fixed;
    left:0;
    top:50%;
    background: #e1e4eb;
    // display: flex;
    // align-items: center;
    // justify-content: center;
    padding:10px 0;
    transform: translate(0,-50%);
    z-index:1000;
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px;
  }
  z-index: 100;
  margin-left: 2px;
  box-shadow: -30px 0 30px -30px rgba(0, 0, 0, 0.2);
  border-left: 1px solid rgb(238, 238, 238);
  
  width: 65%;
  background: #fff;

}
.active{
  border-left:1px solid #40a9ff;
  .up-btn{
    background: #40a9ff;
    color:#fff;
  }
}





.slide-fade-enter-active {
  transition: all .6s ease;
}
.slide-fade-leave-active {
  transition: all .2s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  width:0;
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}


.slide-fadeone-enter-active {
  transition: all .6s ease;
}
.slide-fadeone-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  width:0;
}
.slide-fadeone-enter, .slide-fadeone-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(-10px);
  opacity: 0;
}


.item-active{
  background:#40a9ff;
  color:#fff
}
</style>