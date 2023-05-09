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
              <a-tabs  :activeKey="tabSel" @change="callback">
                <a-tab-pane tab="用户" key="1" forceRender>
                  <template>
                    规则：
                    <a-radio-group v-model="userTree.defValue" @change="onUserChange">
                      <!-- <a-radio-group v-model="userTree.defValue"  @change="value => onUserChange(value, item.code)" > -->
                      <a-radio
                        :value="item.code"
                        v-for="item in userTree.rules"
                        :key="item.code"
                      >{{item.text}}</a-radio>
                    </a-radio-group>
                    <a-tree
                      v-model="userTree.checkedKeys"
                      checkable
                      :tree-data="userTree.list"
                      :default-expanded-keys="['-1']"
                      @check="onUserCheck"
                    />
                  </template>
                </a-tab-pane>
                <a-tab-pane tab="角色" key="2">
                  <template>
                    规则：
                    <a-radio-group v-model="roleTree.defValue" @change="onRoleChange">
                      <a-radio
                        :value="item.code"
                        v-for="item in roleTree.rules"
                        :key="item.code"
                      >{{item.text}}</a-radio>
                    </a-radio-group>
                    <a-tree
                      v-model="roleTree.checkedKeys"
                      checkable
                      :default-expanded-keys="['-1']"
                      :tree-data="roleTree.list"
                      @check="onRoleCheck"
                    />
                  </template>
                </a-tab-pane>
                <a-tab-pane tab="岗位" key="3">
                  <template>
                    规则：
                    <a-radio-group v-model="positionTree.defValue" @change="onPositionChange">
                      <a-radio
                        :value="item.code"
                        v-for="item in positionTree.rules"
                        :key="item.code"
                      >{{item.text}}</a-radio>
                    </a-radio-group>
                    <a-tree
                      v-model="positionTree.checkedKeys"
                      checkable
                      :default-expanded-keys="['-1']"
                      :tree-data="positionTree.list"
                      @check="onPositionCheck"
                    />
                  </template>
                </a-tab-pane>
                <a-tab-pane tab="自定义" key="4">
                  <a-input v-model="customizeTree.defValue" type="textarea" />
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
      userTree: { defValue: '', rules: [], retRules: {}, key: '', checkedKeys: [], returnItem: [], list: [] },
      roleTree: { defValue: '', rules: [], retRules: {}, key: '', checkedKeys: [], returnItem: [], list: [] },
      positionTree: { defValue: '', rules: [], retRules: {}, key: '', checkedKeys: [], returnItem: [], list: [] },
      customizeTree: { defValue: '', rules: [], retRules: {}, key: '', checkedKeys: [], returnItem: [], list: [] },
      retKey: '',
      retItem: null,
      haveItem: { key: '', rule: '', ids: [] }
    }
  },
 
  methods: {
    onUserChange(obj) {
      var _code = obj.target.value
      var _text = ''
      this.userTree.rules.forEach(item => {
        if (item.code == _code) {
          _text = item.text
          return
        }
      })
      this.userTree.retRules = { code: _code, text: _text }
    },
    onRoleChange(obj) {
      var _code = obj.target.value
      var _text = ''
      this.roleTree.rules.forEach(item => {
        if (item.code == _code) {
          _text = item.text
          return
        }
      })
      this.roleTree.retRules = { code: _code, text: _text }
    },
    onPositionChange(obj) {
      var _code = obj.target.value
      var _text = ''
      this.positionTree.rules.forEach(item => {
        if (item.code == _code) {
          _text = item.text
          return
        }
      })
      this.positionTree.retRules = { code: _code, text: _text }
    },
    onUserCheck(checkedKeys, info) {
      this.userTree.returnItem = []
      var checkedNodes = info.checkedNodes
      checkedNodes.forEach(item => {
        this.userTree.returnItem.push({ key: item.key, title: item.data.props.title })
      })
    },
    onRoleCheck(checkedKeys, info) {
      this.roleTree.returnItem = []
      var checkedNodes = info.checkedNodes
      checkedNodes.forEach(item => {
        this.roleTree.returnItem.push({ key: item.key, title: item.data.props.title })
      })
    },
    onPositionCheck(checkedKeys, info) {
      this.positionTree.returnItem = []
      var checkedNodes = info.checkedNodes
      checkedNodes.forEach(item => {
        this.positionTree.returnItem.push({ key: item.key, title: item.data.props.title })
      })
    },
    show() {
      this.visible = true
    },
    callback(key) {
      console.log(key)
      this.tabSel = key
    },
    handleOk() {
      if (this.tabSel == 1) {
        this.$emit('ok', this.tabSel, this.userTree)
      }
      if (this.tabSel == 2) {
        this.$emit('ok', this.tabSel, this.roleTree)
      }
      if (this.tabSel == 3) {
        this.$emit('ok', this.tabSel, this.positionTree)
      }
      if (this.tabSel == 4) {
        this.$emit('ok', this.tabSel, this.customizeTree)
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