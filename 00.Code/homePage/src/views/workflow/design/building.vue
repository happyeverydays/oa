<template>
  <div class="main-box">
    <div class="content-box" ref="content">
      <a-button
        type="primary"
        ref="saveDiagram"
        @click="saveDiagram"
        class="margin-size"
        href="javascript:void(0);"
      >保存流程</a-button>
      <div class="canvas" id="canvas" ref="canvas"></div>
      <div id="js-properties-panel" :style="{height:height+'px'}" class="panel"></div>
    </div>
    <!-- 流程用户接口-->
    <Assignee-Modal ref="AssigneeModalForm" @ok="AssigneeModalFormOk"></Assignee-Modal>
    <!-- 流程候选人接口  -->
    <CandidateUsers-Modal ref="CandidateUsersModalForm" @ok="CandidateUsersModalFormOk"></CandidateUsers-Modal>
    <!--03.会签用户接口-->
    <Collections-Modal ref="CollectionsModalForm" @ok="CollectionsModalFormOk"></Collections-Modal>

    <!--03.会签用户接口-->
    <CompletionCondition-Modal
      ref="CompletionConditionModalForm"
      @ok="CompletionConditionModalFormOk"
    ></CompletionCondition-Modal>
  </div>
</template> 

<script>
import { httpAction } from '@/api/manage'
import customTranslate from '@/assets/customTranslate/customTranslate'
import { axios } from '@/utils/request'
// import pick from 'lodash.pick'
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
// /*右边工具栏样式*/
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css'
import BpmnViewer from 'bpmn-js'
import BpmnModeler from 'bpmn-js/lib/Modeler'
import propertiesPanelModule from 'bpmn-js-properties-panel'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'

import $ from 'jquery'
import { getProcessAssignees, getProcessCandidates, getCollections, getCompletionConditions } from '@/api/api'
import { getAction, postAction, deleteAction } from '@/api/manage'

import CandidateUsersModalForm from './modules/CandidateModal.vue'
import AssigneeModal from './modules/AssigneeModal.vue'
import CollectionsModalForm from './modules/CollectionsModal.vue'
import CompletionConditionModalForm from './modules/CompletionConditionModal.vue'

export default {
  name: 'BuildingModal',
  props: ['buildId'],
  components: {
    'CandidateUsers-Modal': CandidateUsersModalForm,
    'Assignee-Modal': AssigneeModal,
    'Collections-Modal': CollectionsModalForm,
    'CompletionCondition-Modal': CompletionConditionModalForm
  },
  data() {
    return {
      // bpmn建模器
      bpmnModeler: null,
      container: null,
      canvas: null,
      bpmnXmlStr: '',
      height: 100,
      visible: false,
      url: {
        add: '/base.building/building/add',
        edit: '/base.building/building/edit',
        getxml: '/workflow-rest/engine/default/process-definition',
        savexml: '/workflow/engine/default/deployment/create'
      },
      candidateUsers: {
        selUserEvent: null, //点击选择的对象，为后期找到赋值的地方做准备
        tabSel: '1', //选择对象的tab索引
        selItem: null,
        tagId: '',
        userTree: { key: '', rules: [], data: [] },
        roleTree: { key: '', rules: [], data: [] },
        positionTree: { key: '', rules: [], data: [] },
        customizeTree: { key: '', data: [] },
        haveItem: { key: '', rule: '', ids: [], value: '' }
      },
      assignee: {
        selUserEvent: null,
        selItem: null,
        tagId: '',
        data: [],
        value: ''
      },
      Collections: {
        selUserEvent: null,
        tabSel: '1',
        selItem: null,
        tagId: '',
        userTree: { key: '', rules: [], data: [] },
        roleTree: { key: '', rules: [], data: [] },
        positionTree: { key: '', rules: [], data: [] },
        customizeTree: { key: '', data: [] },
        haveItem: { key: '', rule: '', ids: [], value: '' }
      },
      CompletionCondition: {
        selUserEvent: null,
        tabSel: '1',
        selItem: null,
        tagId: '',
        commonConditionTree: { key: '', data: [] },
        expressionConditionTree: { key: '', data: '' },
        haveItem: { key: '', ids: [], value: '' }
      }
    }
  },
  created() {},
  mounted() {
    window.OpencandidateUsersDiag = this.OpencandidateUsersDiag
    window.RemovecandidateUsersTag = this.RemovecandidateUsersTag
    window.OpenassigneeDiag = this.OpenassigneeDiag
    window.RemoveassigneeTag = this.RemoveassigneeTag
    window.OpenmultiInstance_collectionDiag = this.OpenmultiInstance_collectionDiag
    window.RemovemultiInstance_collectionTag = this.RemovemultiInstance_collectionTag
    window.OpenmultiInstance_completionConditionDiag = this.OpenmultiInstance_completionConditionDiag
    window.RemovemultiInstance_completionConditionTag = this.RemovemultiInstance_completionConditionTag
    this.height = document.body.clientHeight - 100

    // 获取到属性ref为“content”的dom节点
    this.container = this.$refs.content
    // 获取到属性ref为“canvas”的dom节点
    const canvas = this.$refs.canvas
    // 建模，官方文档这里讲的很详细
    var customTranslateModule = {
      translate: ['value', customTranslate]
    }

    this.bpmnModeler = new BpmnModeler({
      container: canvas,
      // 添加控制板
      propertiesPanel: {
        parent: '#js-properties-panel'
      },
      additionalModules: [
        // 左边工具栏以及节点
        propertiesProviderModule,
        // 右边的工具栏
        propertiesPanelModule,
        customTranslateModule
      ],
      moddleExtensions: {
        camunda: camundaModdleDescriptor
      }
    })

    this.createNewDiagram(this.buildId)
    this.Init()
  },

  methods: {
    Init() {
      this.loadProcessAssignees() //加载工作流用户接口数据
      this.loadProcessCandidates() //加载工作流候选用户接口数据
      this.loadCollections() //加载会签用户接口
      this.loadCompletionConditions() //会签权重接口
      this.addModelerListener() //添加工作流监听事件
    },
    //加载接口中的数据
    loadProcessCandidates() {
      getProcessCandidates().then(res => {
        if (res.success) {
          var result = res.result //解析返回值中的用户列表

          this.candidateUsers.userTree.rules = []
          this.candidateUsers.userTree.key = Object.keys(result)[0]
          var userItems = result[this.candidateUsers.userTree.key].data.map(function(item) {
            return { title: item.name, key: item.id }
          })
          result[this.candidateUsers.userTree.key].rules.forEach(item => {
            var split = item.split('|')
            this.candidateUsers.userTree.rules.push({ code: split[0], text: split[1] })
          })
          this.candidateUsers.userTree.data = [{ title: '选择用户', key: '-1', children: userItems }] //解析返回值中的角色列表

          this.candidateUsers.roleTree.rules = []
          this.candidateUsers.roleTree.key = Object.keys(result)[1]
          var roleItems = result[this.candidateUsers.roleTree.key].data.map(function(item) {
            return { title: item.name, key: item.id }
          })
          result[this.candidateUsers.roleTree.key].rules.forEach(item => {
            var split = item.split('|')
            this.candidateUsers.roleTree.rules.push({ code: split[0], text: split[1] })
          })
          this.candidateUsers.roleTree.data = [{ title: '选择角色', key: '-1', children: roleItems }] //解析返回值中的岗位数据

          this.candidateUsers.positionTree.rules = []
          this.candidateUsers.positionTree.key = Object.keys(result)[2]
          var positionItems = result[this.candidateUsers.positionTree.key].data.map(function(item) {
            return { title: item.name, key: item.id }
          })

          result[this.candidateUsers.positionTree.key].rules.forEach(item => {
            var split = item.split('|')
            this.candidateUsers.positionTree.rules.push({ code: split[0], text: split[1] })
          })
          this.candidateUsers.positionTree.data = [{ title: '选择岗位', key: '-1', children: positionItems }] // 解析返回值中的自定义数据

          this.candidateUsers.customizeTree.key = Object.keys(result)[3]
          this.candidateUsers.customizeTree.data = result[this.candidateUsers.customizeTree.key].data
        } else {
          that.$message.warning(res.message)
        }
      })
    },
    //加载工作流用户接口数据
    loadProcessAssignees() {
      getProcessAssignees().then(res => {
        if (res.success) {
          var userItems = res.result.map(function(item) {
            return { title: item.name, key: item.id }
          })
          this.assignee.data = [{ title: '选择用户', key: '-1', children: userItems }]
          console.log(this.assignee.data)
        } else {
          that.$message.warning(res.message)
        }
      })
    },
    //加载会签用户接口
    loadCollections() {
      getCollections().then(res => {
        if (res.success) {
          var result = res.result //解析返回值中的用户列表

          this.Collections.userTree.rules = []
          this.Collections.userTree.key = Object.keys(result)[0]
          var userItems = result[this.Collections.userTree.key].data.map(function(item) {
            return { title: item.name, key: item.id }
          })
          result[this.Collections.userTree.key].rules.forEach(item => {
            var split = item.split('|')
            this.Collections.userTree.rules.push({ code: split[0], text: split[1] })
          })
          this.Collections.userTree.data = [{ title: '选择用户', key: '-1', children: userItems }] //解析返回值中的角色列表

          this.Collections.roleTree.rules = []
          this.Collections.roleTree.key = Object.keys(result)[1]
          var roleItems = result[this.Collections.roleTree.key].data.map(function(item) {
            return { title: item.name, key: item.id }
          })
          result[this.Collections.roleTree.key].rules.forEach(item => {
            var split = item.split('|')
            this.Collections.roleTree.rules.push({ code: split[0], text: split[1] })
          })
          this.Collections.roleTree.data = [{ title: '选择角色', key: '-1', children: roleItems }] //解析返回值中的岗位数据

          this.Collections.positionTree.rules = []
          this.Collections.positionTree.key = Object.keys(result)[2]
          var positionItems = result[this.Collections.positionTree.key].data.map(function(item) {
            return { title: item.name, key: item.id }
          })

          result[this.Collections.positionTree.key].rules.forEach(item => {
            var split = item.split('|')
            this.Collections.positionTree.rules.push({ code: split[0], text: split[1] })
          })
          this.Collections.positionTree.data = [{ title: '选择岗位', key: '-1', children: positionItems }] // 解析返回值中的自定义数据

          this.Collections.customizeTree.key = Object.keys(result)[3]
          this.Collections.customizeTree.data = result[this.Collections.customizeTree.key].data
        } else {
          that.$message.warning(res.message)
        }
      })
    },
    //会签权重接口
    loadCompletionConditions() {
      getCompletionConditions().then(res => {
        if (res.success) {
          var result = res.result //解析返回值中的用户列表

          var _key = Object.keys(result)[0]
          this.CompletionCondition.commonConditionTree.key = _key
          var conditionsItem = result[_key].data.map(function(item) {
            return { title: item.text, key: item.key }
          })
          this.CompletionCondition.commonConditionTree.data = [
            { title: '选择规则', key: '-1', children: conditionsItem }
          ]

          var _expressionKey = Object.keys(result)[1]
          this.CompletionCondition.expressionConditionTree.key = _expressionKey
          this.CompletionCondition.expressionConditionTree.data = result[_expressionKey].data + ''
        } else {
          that.$message.warning(res.message)
        }
      })
    },
    //添加工作流节点点击事件
    addModelerListener() {
      let that = this

      const bpmnjs = this.bpmnModeler
      const eventBus = bpmnjs.get('eventBus') // 需要使用eventBus
      const eventTypes = ['element.changed'] // 需要监听的事件集合//'element.click',

      eventBus.on('element.click', function(e) {
        that.NodeClick(that, e)
      })
      eventBus.on('element.changed', function(e) {
        that.NodeClick(that, e)
      })
    },
    NodeClick(that, e) {
      var busissObj = e.element.businessObject //属性对象
      that.NodeCandidateUsers(that, busissObj)
      that.NodeAssignee(that, busissObj)
      that.NodeCollection(that, busissObj)
      that.NodeCompletionCondition(that, busissObj)
    },
    NodeCandidateUsers(that, busissObj) {
      var _candidateUsers = busissObj.candidateUsers //具体的候选用户
      if (_candidateUsers != undefined) {
        console.log(_candidateUsers)
        var _candiateUser_Express = _candidateUsers.match(/\(([^)]*)\)/)[1].match(/\'([^\']*)\'/g) //获取括号内的内容

        var item1 = _candiateUser_Express[0].replace(/\'/g, '')
        var item2 = _candiateUser_Express[1].replace(/\'/g, '')
        var item3 = _candiateUser_Express[2].replace(/\'/g, '')
        that.candidateUsers.haveItem.key = item1
        that.candidateUsers.haveItem.rule = item2
        if (item1 == 'customize') {
          that.candidateUsers.tabSel = '4'
          that.candidateUsers.haveItem.value = item3
        } else {
          switch (item1) {
            case 'user':
              that.candidateUsers.tabSel = '1'
              break
            case 'role':
              that.candidateUsers.tabSel = '2'
              break
            case 'position':
              that.candidateUsers.tabSel = '3'
              break
          }
          if (item3 != '') {
            that.candidateUsers.haveItem.ids = item3.split(',')
          }
        }
        that.BuildCandidateUsersOldHtml()
      }
    },
    NodeAssignee(that, busissObj) {
      var _assignee = busissObj.assignee //得到工作流用户ID
      if (_assignee != undefined) {
        that.assignee.value = _assignee
        that.BuildAssigneeOldHtml()
      }
    },
    NodeCollection(that, busissObj) {
      var _collection = busissObj.loopCharacteristics.collection
      if (_collection != undefined) {
        var _collection_Express = _collection.match(/\(([^)]*)\)/)[1].match(/\'([^\']*)\'/g) //获取括号内的内容

        var item1 = _collection_Express[0].replace(/\'/g, '')
        var item2 = _collection_Express[1].replace(/\'/g, '')
        var item3 = _collection_Express[2].replace(/\'/g, '')
        that.Collections.haveItem.key = item1
        that.Collections.haveItem.rule = item2
        if (item1 == 'customize') {
          that.Collections.tabSel = '4'
          that.Collections.haveItem.value = item3
        } else {
          switch (item1) {
            case 'user':
              that.Collections.tabSel = '1'
              break
            case 'role':
              that.Collections.tabSel = '2'
              break
            case 'position':
              that.Collections.tabSel = '3'
              break
          }
          if (item3 != '') {
            that.Collections.haveItem.ids = item3.split(',')
          }
        }
        that.BuildCollectionOldHtml()
      }
    },
    NodeCompletionCondition(that, busissObj) {
      var _completionCondition = busissObj.loopCharacteristics.completionCondition.body
      if (_completionCondition != undefined) { 
        that.CompletionCondition.haveItem.ids = []
        var _split = _completionCondition.split('|')
        var item1 = _split[0]
        var item2 = _split[1]
        if (item1 == 'commonCondition') {
          that.CompletionCondition.tabSel = '1'
          that.CompletionCondition.haveItem.key = item2
          that.CompletionCondition.haveItem.ids.push(item2)
        } else {
          that.CompletionCondition.tabSel = '2'
          that.CompletionCondition.haveItem.value = item2
        }

        that.BuildCompletionConditionHtml()
      }
    },
    //回显工作流中存储的信息
    BuildCandidateUsersOldHtml() {
      var htmlStr = ''
      if (this.candidateUsers.tabSel == '4') {
        htmlStr = this.candidateUsers.haveItem.value
      } else {
        var items = null
        switch (this.candidateUsers.tabSel) {
          case '1':
            items = this.getCandidateUsersOldItem(this.candidateUsers.userTree.data)
            break
          case '2':
            items = this.getCandidateUsersOldItem(this.candidateUsers.roleTree.data)
            break
          case '3':
            items = this.getCandidateUsersOldItem(this.candidateUsers.positionTree.data)
            break
        }

        items.forEach(item => {
          htmlStr += this.getTagItem(this.candidateUsers.tabSel, 'candidateUsers', item)
        })
      }
      $('#tagBox_candidateUsers').html(htmlStr)
    },
    BuildAssigneeOldHtml() {
      var _item = this.getAssigneeOldItem()
      var htmlStr = this.getTagItem('5', 'assignee', _item)
      $('#tagBox_assignee').html(htmlStr)
    },
    BuildCollectionOldHtml() {
      var htmlStr = ''
      if (this.Collections.tabSel == '4') {
        htmlStr = this.Collections.haveItem.value
      } else {
        var items = null
        switch (this.Collections.tabSel) {
          case '1':
            items = this.getCollectionOldItem(this.Collections.userTree.data)
            break
          case '2':
            items = this.getCollectionOldItem(this.Collections.roleTree.data)
            break
          case '3':
            items = this.getCollectionOldItem(this.Collections.positionTree.data)
            break
        }

        items.forEach(item => {
          htmlStr += this.getTagItem(this.Collections.tabSel, 'multiInstance_collection', item)
        })
      }
      $('#tagBox_multiInstance_collection').html(htmlStr)
    },
    BuildCompletionConditionHtml() {
      var htmlStr = '' 
      var item = this.getCompletionConditionOldItem(this.CompletionCondition.commonConditionTree.data)[0]
      htmlStr = this.getTagItem('6', 'multiInstance_completionCondition', item)
      $('#tagBox_multiInstance_completionCondition').html(htmlStr)
    },
    //工作流用户返回事件
    AssigneeModalFormOk(retInfo) {
      this.assignee.selItem = retInfo
      var htmlStr = this.getTagItem('', this.assignee.tagId, retInfo.returnItem)
      // $('#camunda-' + this.assignee.tagId).val(retInfo.returnItem.key)
      $('#tagBox_' + this.assignee.tagId).html(htmlStr)

      var input = $(this.assignee.selUserEvent)
        .prev()
        .children()
        .eq(0)
      $(input).val(retInfo.returnItem.key)
      var changeEvent = document.createEvent('HTMLEvents')
      changeEvent.initEvent('change', true, true)
      $(input)[0].dispatchEvent(changeEvent)
    },
    //工作流候选用户返回事件
    CandidateUsersModalFormOk(tabSel, retInfo) {
      var input = $(this.candidateUsers.selUserEvent)
        .prev()
        .children()
        .eq(0)
      this.candidateUsers.tabSel = tabSel.toString()
      this.candidateUsers.selItem = retInfo
      if (this.candidateUsers.selItem != null) {
        let inputIds = ''
        var exprises = ''
        var htmlStr = ''
        if (this.candidateUsers.tabSel == '4') {
          $('#tagBox_' + this.candidateUsers.tagId).html(this.candidateUsers.selItem.defValue)
          exprises = "${expressionUser.candidate('customize','','" + this.candidateUsers.selItem.defValue + "')}"
          $(input).val(exprises)
        } else {
          retInfo.returnItem.forEach(item => {
            inputIds += inputIds == '' ? item.key : ',' + item.key
            console.log(inputIds)
            htmlStr += this.getTagItem(tabSel, this.candidateUsers.tagId, item)
          })
          $('#tagBox_' + this.candidateUsers.tagId).html(htmlStr)
          switch (this.candidateUsers.tabSel) {
            case '1':
              exprises = inputIds
              break
            case '2':
              exprises =
                "${expressionUser.candidate('role','" +
                this.candidateUsers.selItem.retRules.code +
                "','" +
                inputIds +
                "')}"
              break
            case '3':
              exprises =
                "${expressionUser.candidate('position','" +
                this.candidateUsers.selItem.retRules.code +
                "','" +
                inputIds +
                "')}"
              break
          }
        }
        $(input).val(exprises)
        this.listenInput(input)
      }
    },
    CollectionsModalFormOk(tabSel, retInfo) {
      var input = $(this.Collections.selUserEvent)
        .prev()
        .children()
        .eq(0)
      this.Collections.tabSel = tabSel.toString()
      this.Collections.selItem = retInfo
      if (this.Collections.selItem != null) {
        let inputIds = ''
        var exprises = ''
        var htmlStr = ''
        if (this.Collections.tabSel == '4') {
          $('#tagBox_' + this.Collections.tagId).html(this.Collections.selItem.defValue)
          exprises = "${expressionUser.candidate('customize','','" + this.Collections.selItem.defValue + "')}"
          $(input).val(exprises)
        } else {
          retInfo.returnItem.forEach(item => {
            inputIds += inputIds == '' ? item.key : ',' + item.key
            htmlStr += this.getTagItem(tabSel, this.Collections.tagId, item)
          })
          $('#tagBox_' + this.Collections.tagId).html(htmlStr)
          switch (this.Collections.tabSel) {
            case '1':
              exprises = inputIds
              break
            case '2':
              exprises =
                "${expressionUser.candidate('role','" +
                this.Collections.selItem.retRules.code +
                "','" +
                inputIds +
                "')}"
              break
            case '3':
              exprises =
                "${expressionUser.candidate('position','" +
                this.Collections.selItem.retRules.code +
                "','" +
                inputIds +
                "')}"
              break
          }
        }
        $(input).val(exprises)
        this.listenInput(input)
      }
    },
    CompletionConditionModalFormOk(tabSel, retInfo) {
      var input = $(this.CompletionCondition.selUserEvent)
        .prev()
        .children()
        .eq(0)
      this.CompletionCondition.tabSel = tabSel
      this.CompletionCondition.selItem = retInfo
      if (this.CompletionCondition.selItem != null) {
        let inputId = ''
        var exprises = ''
        var htmlStr = ''
        if (this.CompletionCondition.tabSel == '2') {
          // inputId = this.CompletionCondition.selItem.defValue
          inputId = 'expressionCondition|' + this.CompletionCondition.selItem.defValue
          htmlStr = inputId
        } else {
          var item = this.CompletionCondition.selItem.returnItem
          //inputId = item.key
          inputId = 'commonCondition|' + item.key
          htmlStr = this.getTagItem(tabSel, this.CompletionCondition.tagId, item)
        }
        $('#tagBox_' + this.CompletionCondition.tagId).html(htmlStr)
        $(input).val(inputId)
        this.listenInput(input)
      }
    },
    getTagItem(tabSel, parId, item) {
      var htmlStr =
        '<span id="spn_' +
        parId +
        '_' +
        item.key +
        '" style="display: inline-block;margin: 0.3em 0.25em 0.15em;vertical-align: middle;cursor: default;white-space:nowrap;">' +
        '<span  style="display: none;">•</span>' +
        '<span style="display: inline-block;padding: 0.417em 0.417em;font-size: 1em;line-height: 1.4em;  text-shadow: 0.083em 0.083em 0.083em #888;border: 0.083em solid #3C7BA8;color: #FFF;  background: #234B77;background: -webkit-gradient(linear, left top, left bottom, from(#4998CF), to(#234B77));background: -moz-linear-gradient(top, #4998CF, #234B77);  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#4998CF", endColorstr="#234B77");">' +
        '<a href="javascript:void(0)" onclick="Remove' +
        parId +
        "Tag('" +
        tabSel +
        "','" +
        parId +
        "','" +
        item.key +
        '\')" title="Remove Tag" rel="1231231" style="text-decoration: none;display: inline-block;  float: right;  color: #FFF;text-align: center;font-family: \'Comic Sans MS\', Arial, Helvetica, sans-serif;  font-size: 10px;  font-weight: bold;  line-height: 1em;filter:Alpha(opacity=50);margin-left: 0.5em;  padding: 0.35em 0.5em;-moz-border-radius: 1em;  -webkit-border-radius: 1em;border-radius: 1em;-moz-box-shadow: 0 0 3px #FFF;-webkit-box-shadow: 0 0 3px #FFF;  box-shadow: 0 0 3px #FFF;">X</a>' +
        item.title +
        '</span>' +
        '</span>'
      return htmlStr
    },
    getCandidateUsersOldItem(dataItem) {
      var _item = []
      this.candidateUsers.haveItem.ids.forEach(idItem => {
        dataItem[0].children.forEach(item => {
          if (item.key == idItem) {
            _item.push(item)
            return
          }
        })
      })
      return _item
    },
    getCollectionOldItem(dataItem) {
      var _item = []
      this.Collections.haveItem.ids.forEach(idItem => {
        dataItem[0].children.forEach(item => {
          if (item.key == idItem) {
            _item.push(item)
            return
          }
        })
      })
      return _item
    },
    getCompletionConditionOldItem(dataItem) {
      var _item = []
      this.CompletionCondition.haveItem.ids.forEach(idItem => {
        dataItem[0].children.forEach(item => {
          if (item.key == idItem) {
            _item.push(item)
            return
          }
        })
      })
      return _item
    },
    getAssigneeOldItem() {
      var _item = {}
      this.assignee.data[0].children.forEach(item => {
        if (item.key == this.assignee.value) {
          _item = item
          return
        }
      })
      return _item
    },
    //获取候选用户规则的集合
    getRulesItem(dataItem) {
      var _item = {}
      dataItem.forEach(item => {
        if (item.code == this.candidateUsers.haveItem.rule) {
          _item = item
          return
        }
      })
      return _item
    },
    getCollectionsRulesItem(dataItem) {
      var _item = {}
      dataItem.forEach(item => {
        if (item.code == this.Collections.haveItem.rule) {
          _item = item
          return
        }
      })
      return _item
    },
    //打开工作流候选用户选择
    OpencandidateUsersDiag(e, id) {
      this.candidateUsers.tagId = id
      this.candidateUsers.selUserEvent = e
      this.$refs.CandidateUsersModalForm.title = '选择'

      this.$refs.CandidateUsersModalForm.show()
      // userTree: { key: '', rules: [], data: [] },
      this.$refs.CandidateUsersModalForm.userTree.key = this.candidateUsers.userTree.key
      this.$refs.CandidateUsersModalForm.userTree.rules = this.candidateUsers.userTree.rules
      this.$refs.CandidateUsersModalForm.userTree.list = this.candidateUsers.userTree.data

      this.$refs.CandidateUsersModalForm.roleTree.key = this.candidateUsers.roleTree.key
      this.$refs.CandidateUsersModalForm.roleTree.rules = this.candidateUsers.roleTree.rules
      this.$refs.CandidateUsersModalForm.roleTree.list = this.candidateUsers.roleTree.data

      this.$refs.CandidateUsersModalForm.positionTree.key = this.candidateUsers.positionTree.key
      this.$refs.CandidateUsersModalForm.positionTree.rules = this.candidateUsers.positionTree.rules
      this.$refs.CandidateUsersModalForm.positionTree.list = this.candidateUsers.positionTree.data

      this.$refs.CandidateUsersModalForm.customizeTree.key = this.candidateUsers.customizeTree.key
      this.$refs.CandidateUsersModalForm.customizeTree.list = this.candidateUsers.customizeTree.data

      this.$refs.CandidateUsersModalForm.tabSel = this.candidateUsers.tabSel

      switch (this.candidateUsers.tabSel) {
        case '1':
          this.$refs.CandidateUsersModalForm.userTree.defValue = this.candidateUsers.haveItem.rule
          this.$refs.CandidateUsersModalForm.userTree.checkedKeys = this.candidateUsers.haveItem.ids
          this.$refs.CandidateUsersModalForm.userTree.returnItem = this.getCandidateUsersOldItem(
            this.candidateUsers.userTree.data
          )
          this.$refs.CandidateUsersModalForm.userTree.retRules = this.getRulesItem(this.candidateUsers.userTree.rules)
          // console.log(this.$refs.CandidateUsersModalForm.userTree.returnItem)
          break
        case '2':
          this.$refs.CandidateUsersModalForm.roleTree.defValue = this.candidateUsers.haveItem.rule
          this.$refs.CandidateUsersModalForm.roleTree.checkedKeys = this.candidateUsers.haveItem.ids
          this.$refs.CandidateUsersModalForm.roleTree.returnItem = this.getCandidateUsersOldItem(
            this.candidateUsers.roleTree.data
          )
          this.$refs.CandidateUsersModalForm.roleTree.retRules = this.getRulesItem(this.candidateUsers.userTree.rules)
          // console.log(this.$refs.CandidateUsersModalForm.roleTree.returnItem)
          break
        case '3':
          this.$refs.CandidateUsersModalForm.positionTree.defValue = this.candidateUsers.haveItem.rule
          this.$refs.CandidateUsersModalForm.positionTree.checkedKeys = this.candidateUsers.haveItem.ids
          this.$refs.CandidateUsersModalForm.positionTree.returnItem = this.getCandidateUsersOldItem(
            this.candidateUsers.positionTree.data
          )
          this.$refs.CandidateUsersModalForm.positionTree.retRules = this.getRulesItem(
            this.candidateUsers.userTree.rules
          )
          // console.log(this.$refs.CandidateUsersModalForm.positionTree.returnItem)
          break
        case '4':
          // this.$refs.CandidateUsersModalForm.userTree.defValue=this.candidateUsers.haveItem.rule;
          break
      }
      // console.log(this.candidateUsers.haveItem.ids)
    },
    OpenassigneeDiag(e, id) {
      //打开工作流用户选择
      this.assignee.tagId = id
      this.assignee.selUserEvent = e
      this.$refs.AssigneeModalForm.title = '选择'
      this.$refs.AssigneeModalForm.userTree.checkedKeys.push(this.assignee.value)
      this.$refs.AssigneeModalForm.userTree.list = this.assignee.data
      this.$refs.AssigneeModalForm.show()
    },
    OpenmultiInstance_collectionDiag(e, id) {
      this.Collections.tagId = id
      this.Collections.selUserEvent = e
      this.$refs.CollectionsModalForm.title = '选择'
      this.$refs.CollectionsModalForm.show()

      this.$refs.CollectionsModalForm.userTree.key = this.Collections.userTree.key
      this.$refs.CollectionsModalForm.userTree.rules = this.Collections.userTree.rules
      this.$refs.CollectionsModalForm.userTree.list = this.Collections.userTree.data

      this.$refs.CollectionsModalForm.roleTree.key = this.Collections.roleTree.key
      this.$refs.CollectionsModalForm.roleTree.rules = this.Collections.roleTree.rules
      this.$refs.CollectionsModalForm.roleTree.list = this.Collections.roleTree.data

      this.$refs.CollectionsModalForm.positionTree.key = this.Collections.positionTree.key
      this.$refs.CollectionsModalForm.positionTree.rules = this.Collections.positionTree.rules
      this.$refs.CollectionsModalForm.positionTree.list = this.Collections.positionTree.data

      this.$refs.CollectionsModalForm.customizeTree.key = this.Collections.customizeTree.key
      this.$refs.CollectionsModalForm.customizeTree.list = this.Collections.customizeTree.data

      this.$refs.CollectionsModalForm.tabSel = this.Collections.tabSel

      switch (this.Collections.tabSel) {
        case '1':
          this.$refs.CollectionsModalForm.userTree.defValue = this.Collections.haveItem.rule
          this.$refs.CollectionsModalForm.userTree.checkedKeys = this.Collections.haveItem.ids
          this.$refs.CollectionsModalForm.userTree.returnItem = this.getCollectionOldItem(
            this.Collections.userTree.data
          )
          this.$refs.CollectionsModalForm.userTree.retRules = this.getCollectionsRulesItem(
            this.Collections.userTree.rules
          )
          break
        case '2':
          this.$refs.CollectionsModalForm.roleTree.defValue = this.Collections.haveItem.rule
          this.$refs.CollectionsModalForm.roleTree.checkedKeys = this.Collections.haveItem.ids
          this.$refs.CollectionsModalForm.roleTree.returnItem = this.getCollectionOldItem(
            this.Collections.roleTree.data
          )
          this.$refs.CollectionsModalForm.roleTree.retRules = this.getCollectionsRulesItem(
            this.Collections.userTree.rules
          )
          break
        case '3':
          this.$refs.CollectionsModalForm.positionTree.defValue = this.Collections.haveItem.rule
          this.$refs.CollectionsModalForm.positionTree.checkedKeys = this.Collections.haveItem.ids
          this.$refs.CollectionsModalForm.positionTree.returnItem = this.getCollectionOldItem(
            this.Collections.positionTree.data
          )
          this.$refs.CollectionsModalForm.positionTree.retRules = this.getCollectionsRulesItem(
            this.Collections.userTree.rules
          )
          break
        case '4':
          // this.$refs.CandidateUsersModalForm.userTree.defValue=this.candidateUsers.haveItem.rule;
          break
      }
    },
    OpenmultiInstance_completionConditionDiag(e, id) {
      this.CompletionCondition.tagId = id
      this.CompletionCondition.selUserEvent = e
      this.$refs.CompletionConditionModalForm.title = '选择'
      this.$refs.CompletionConditionModalForm.show() 
      
      this.$refs.CompletionConditionModalForm.commonConditionTree.key = this.CompletionCondition.commonConditionTree.key
      this.$refs.CompletionConditionModalForm.commonConditionTree.list = this.CompletionCondition.commonConditionTree.data

      this.$refs.CompletionConditionModalForm.expressionConditionTree.key = this.CompletionCondition.expressionConditionTree.key
      this.$refs.CompletionConditionModalForm.expressionConditionTree.defValue = this.CompletionCondition.expressionConditionTree.data

      this.$refs.CompletionConditionModalForm.tabSel = this.CompletionCondition.tabSel

      if (this.CompletionCondition.tabSel == '1') {
        this.$refs.CompletionConditionModalForm.commonConditionTree.checkedKeys = this.CompletionCondition.haveItem.ids
        this.$refs.CompletionConditionModalForm.commonConditionTree.returnItem = this.getCompletionConditionOldItem(
          this.CompletionCondition.commonConditionTree.data
        )
      }
    },
    //移除工作流用户Tag
    RemovecandidateUsersTag(tabSel, tagId, key) {
      $('#spn_candidateUsers_' + key).remove()
      var _express = ''
      if (this.candidateUsers.selUserEvent != null) {
        if (this.candidateUsers.tabSel == '4') {
        } else {
          this.candidateUsers.selItem.returnItem.some((item, i) => {
            if (item.key == key) {
              this.candidateUsers.selItem.returnItem.splice(i, 1)
              return true
            }
          })
          this.candidateUsers.selItem.checkedKeys.splice($.inArray(key, this.candidateUsers.selItem.checkedKeys), 1)
          switch (this.candidateUsers.tabSel) {
            case '1':
              exprises = this.candidateUsers.selItem.checkedKeys
              break
            case '2':
              _express =
                "${expressionUser.candidate('role','" +
                this.candidateUsers.selItem.retRules.code +
                "','" +
                this.candidateUsers.selItem.checkedKeys.toString() +
                "')}"
              break
            case '3':
              _express =
                "${expressionUser.candidate('position','" +
                this.candidateUsers.selItem.retRules.code +
                "','" +
                this.candidateUsers.selItem.checkedKeys.toString() +
                "')}"
              break
          }
        }
      } else {
        if (this.candidateUsers.tabSel == '4') {
        } else {
          this.candidateUsers.haveItem.ids.splice($.inArray(key, this.candidateUsers.haveItem.ids), 1)
          switch (this.candidateUsers.tabSel) {
            case '1':
              exprises = this.candidateUsers.haveItem.ids
              break
            case '2':
              _express =
                "${expressionUser.candidate('role','" +
                this.candidateUsers.haveItem.rule +
                "','" +
                this.candidateUsers.haveItem.ids.toString() +
                "')}"
              break
            case '3':
              _express =
                "${expressionUser.candidate('position','" +
                this.candidateUsers.haveItem.rule +
                "','" +
                this.candidateUsers.haveItem.ids.toString() +
                "')}"
              break
          }
        }
      }
      var input = $('#camunda-candidateUsers')

      $(input).val(_express)
      this.listenInput(input)
    },
    //移除工作流用户tag
    RemoveassigneeTag(tabSel, tagId, key) {
      //移除工作流用户tag
      $('#spn_' + tagId + '_' + key).remove()
      if (this.assignee.selUserEvent != null) {
        this.assignee.selItem.checkedKeys = []
        this.assignee.selItem.returnItem = {}
      } else {
        this.assignee.value = ''
      }
      var input = $('#camunda-assignee')
      $(input).val('')
      this.listenInput(input)
    },
    //移除会签用户
    RemovemultiInstance_collectionTag(tabSel, tagId, key) {
      $('#spn_' + tagId + '_' + key).remove()
      var _express = ''
      if (this.collection.selUserEvent != null) {
        if (this.collection.tabSel == '4') {
        } else {
          this.collection.selItem.returnItem.some((item, i) => {
            if (item.key == key) {
              this.collection.selItem.returnItem.splice(i, 1)
              return true
            }
          })
          this.collection.selItem.checkedKeys.splice($.inArray(key, this.collection.selItem.checkedKeys), 1)
          switch (this.collection.tabSel) {
            case '1':
              exprises = this.collection.selItem.checkedKeys
              break
            case '2':
              _express =
                "${expressionUser.candidate('role','" +
                this.collection.selItem.retRules.code +
                "','" +
                this.collection.selItem.checkedKeys.toString() +
                "')}"
              break
            case '3':
              _express =
                "${expressionUser.candidate('position','" +
                this.collection.selItem.retRules.code +
                "','" +
                this.collection.selItem.checkedKeys.toString() +
                "')}"
              break
          }
        }
      } else {
        if (this.collection.tabSel == '4') {
        } else {
          this.collection.haveItem.ids.splice($.inArray(key, this.collection.haveItem.ids), 1)
          switch (this.collection.tabSel) {
            case '1':
              exprises = this.collection.haveItem.ids
              break
            case '2':
              _express =
                "${expressionUser.candidate('role','" +
                this.collection.haveItem.rule +
                "','" +
                this.collection.haveItem.ids.toString() +
                "')}"
              break
            case '3':
              _express =
                "${expressionUser.candidate('position','" +
                this.collection.haveItem.rule +
                "','" +
                this.collection.haveItem.ids.toString() +
                "')}"
              break
          }
        }
      }
      var input = $('#camunda-collection')

      $(input).val(_express)
      this.listenInput(input)
    },
    //移除会签权重标签和数据
    RemovemultiInstance_completionConditionTag(tabSel, tagId, key) {
      $('#spn_' + tagId + '_' + key).remove()
      if (this.completionCondition.selUserEvent != null) {
        this.completionCondition.selItem.checkedKeys = []
        this.completionCondition.selItem.returnItem = {}
      } else {
        this.completionCondition.value = ''
      }
      var input = $('#camunda-multiInstance-completionCondition')
      $(input).val('')
      this.listenInput(input)
    },
    listenInput(input) {
      var changeEvent = document.createEvent('HTMLEvents')
      changeEvent.initEvent('change', true, true)
      var inn = $(input)[0]
      $(input)[0].dispatchEvent(changeEvent)
    },
    createNewDiagram(taskid) {
      this.bpmnXmlStr = ''
      let that = this
      if (taskid) {
        let url = this.url.getxml + '/' + taskid + '/xml'
        axios
          .get(url)
          .then(res => {
            console.log('加载....Start')
            console.log(res.bpmn20Xml)
            console.log('加载.... End')
            that.bpmnXmlStr = res.bpmn20Xml
            // 将字符串转换成图显示出来
            this.bpmnModeler.importXML(that.bpmnXmlStr, function(err) {
              if (err) {
                console.error(err)
              } else {
                // 这里还没用到这个，先注释掉吧
                //  that.bpmnModeler.get('propertiesPanel').attachTo('#other-properties');
                document.getElementById('camunda-process-is-executable').checked = true
              }
            })
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        that.bpmnXmlStr =
          '<?xml version="1.0" encoding="UTF-8"?>' +
          '<bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" id="Definitions_0fppxr8" targetNamespace="http://bpmn.io/schema/bpmn">' +
          '<bpmn:process id="Process_1" isExecutable="false" />' +
          '<bpmndi:BPMNDiagram id="BPMNDiagram_1">' +
          '<bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1" />' +
          '</bpmndi:BPMNDiagram>' +
          '</bpmn:definitions>'
        // 将字符串转换成图显示出来

        this.bpmnModeler.importXML(that.bpmnXmlStr, function(err) {
          if (err) {
            console.error(err)
          } else {
            // 这里还没用到这个，先注释掉吧
            // that.success()
            document.getElementById('camunda-process-is-executable').checked = true
          }
        })
      }
    },
    saveDiagram() {
      let that = this
      // console.log(this.bpmnModeler)
      this.$nextTick(() => {
        this.bpmnModeler.saveXML({ format: true }, function(err, xml) {
          if (err) {
            return console.error('could not save BPMN 2.0 diagram', err)
          }
          console.log('保存后....Start')
          console.log(xml)
          console.log('保存后....End')
          let name = document.getElementById('camunda-name').innerText
          // alert(name)

          let params = {
            deploymentName: '报销流程',
            deploymentSource: true,
            resourceName: '报销流程' + '.bpmn',
            resourceText: xml
          }
          const formData = new FormData()
          Object.keys(params).forEach(key => {
            formData.append(key, params[key])
          })

          axios({
            url: that.url.savexml,
            method: 'post',
            data: formData,
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).then(respanse => {
            that.$message.success('保存成功！')
          })
        })
      })
    },

    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(this.model, 'companyId', 'areaId', 'name', 'type', 'location', 'gpsLati', 'gpsLongi')
        )
      })
    }
  }
}
</script>
<style lang="scss">
/*左边工具栏以及编辑节点的样式*/

.content-box {
  position: absolute;
  background-color: #ffffff;
  width: calc(100% - 50px);
  // height: 1300px;
  height: 100%;
}
.canvas {
  width: 100%;
  height: 100%;
}
.panel {
  position: absolute;
  right: 0;
  top: 0;
  width: 300px;

  max-height: 80%;
  overflow-y: scroll;
}

.margin-size {
  margin: 10px;
  position: absolute;
  left: 100px;
  top: -20px;
  z-index: 1000;
}
</style>
