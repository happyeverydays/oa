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
    <a-spin :spinning="confirmLoading">
      <template>
        <a-tree
          v-model="userTree.checkedKeys"
          checkable="true"
          :defaultExpandAll="true"
          :tree-data="userTree.list"
          @check="onUserCheck"
        />
      </template>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import moment from 'moment'
import Vue from 'vue'
// import { filterObj } from '@/utils/util' 

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
      userTree: {
        key: '',
        checkedKeys: [],
        returnItem: {},
        list: []
      }
    }
  }, 
  methods: {
    onUserCheck(checkedKeys, info) {
      this.userTree.checkedKeys = []
      var _checkedKey = checkedKeys[checkedKeys.length - 1]
      this.userTree.checkedKeys.push(_checkedKey)
      var checkedNodes = info.checkedNodes
      checkedNodes.forEach(item => {
        if (item.key == _checkedKey) {
          this.userTree.returnItem = { key: item.key, title: item.data.props.title }
        }
      })
    },
    show() {
      this.visible = true 
    },
    handleOk() {
      this.$emit('ok', this.userTree)
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