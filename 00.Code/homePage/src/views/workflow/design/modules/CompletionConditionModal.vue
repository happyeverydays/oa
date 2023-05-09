<template>
  <a-modal
    :title="title"
    :width="800"
    :ok="false"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <div style="width:99%;height:600px; ">
      <a-spin :spinning="confirmLoading">
        <a-row :gutter="10">
          <a-col :md="24" :sm="24">
            <a-card :bordered="false">
              <a-tabs :activeKey="tabSel" @change="callback">
                <a-tab-pane tab="常用规则" key="1" forceRender>
                  <template>
                    <a-tree
                      v-model="commonConditionTree.checkedKeys"
                      checkable
                      :tree-data="commonConditionTree.list"
                      :default-expanded-keys="['-1']"
                      @check="onCommonConditionCheck"
                    />
                  </template>
                </a-tab-pane>
                <a-tab-pane tab="自定义" key="2">
                  <a-input v-model="expressionConditionTree.defValue" type="textarea" />
                </a-tab-pane>
              </a-tabs>
            </a-card>
          </a-col>
        </a-row>
      </a-spin>
    </div>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import moment from 'moment'
import Vue from 'vue'
import { filterObj } from '@/utils/util'

export default {
  name: 'UserSelect',
  data() {
    return {
      title: '操作',
      seen: false,
      visible: false,
      condition: true,
      disableSubmit: false,
      model: {},
      menuhidden: false,
      confirmLoading: false,
      menuusing: true,
      tabSel: '1',
      commonConditionTree: {
        defValue: '', 
        key: '',
        checkedKeys: [],
        returnItem: [],
        list: []
      },
      expressionConditionTree: {
        defValue: '', 
        key: '',
        checkedKeys: [],
        returnItem: [],
        list: []
      },
      retKey: '',
      retItem: null,
      haveItem: { key: '', rule: '', ids: [] }
    }
  },

  methods: {
    onCommonConditionCheck(checkedKeys, info) { 
      this.commonConditionTree.checkedKeys = []
      var _checkedKey = checkedKeys[checkedKeys.length - 1]
      this.commonConditionTree.checkedKeys.push(_checkedKey)
      var checkedNodes = info.checkedNodes
      checkedNodes.forEach(item => {
        if (item.key == _checkedKey) {
          this.commonConditionTree.returnItem = { key: item.key, title: item.data.props.title }
        }
      }) 
    },
    show() {
      this.visible = true
    },
    callback(key) { 
      this.tabSel = key
    },
    handleOk() {
      if (this.tabSel == 1) {
        this.$emit('ok', this.tabSel, this.commonConditionTree)
      }
      if (this.tabSel == 2) {
        this.$emit('ok', this.tabSel, this.expressionConditionTree)
      }
      this.visible = false
    },
    handleCancel() {
      this.close()
    },
    close() {
      this.$emit('close')
      this.disableSubmit = false
      this.visible = false
    }
  }
}
</script>
<style scoped>
</style>

<style scoped>
@import '~@assets/less/common.less';
.modal-bg {
  width: 100%;
  height: 500px;
  background: red;
}
</style>