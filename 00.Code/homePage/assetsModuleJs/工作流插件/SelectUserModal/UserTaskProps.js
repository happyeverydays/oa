
var is = require('bpmn-js/lib/util/ModelUtil').is,
  entryFactory = require('../../../factory/EntryFactory');

var tokenObject = require("@/store/mutation-types")
module.exports = function (group, element, translate) {
  if (is(element, 'camunda:Assignable')) {

    const ACCESS_TOKEN = tokenObject.ACCESS_TOKEN
    const VueObject = require('vue')
    const Vue = VueObject.default
    const axios = require('../../../../../../node_modules/axios')


    let userOptions = []
    let userList = window.localStorage.getItem('userList')
    if (userList) {
      userList = JSON.parse(userList)
    } userList.forEach(item => {
      "use strict";
      userOptions.push({
        value: item.id,
        name: item.realname
      })
    })
    // Assignee
    // group.entries.push(entryFactory.multipleBox({
    //   id: 'assignee',
    //   label: translate('Assignee'),
    //   modelProperty: 'assignee',
    //   selectOptions: userOptions
    // }));

    group.entries.push(entryFactory.tabField({
      id: 'assignee',
      label: translate('Assignee'),
      modelProperty: 'assignee',
      selectOptions: userOptions
    }));



    // Candidate Users
    // group.entries.push(entryFactory.multipleBox({
    //   id: 'candidateUsers',
    //   label: translate('Candidate Users'),
    //   modelProperty: 'candidateUsers',
    //   selectOptions: userOptions

    // }));

    group.entries.push(entryFactory.tabField({
      id: 'candidateUsers',
      label: translate('Candidate Users'),
      modelProperty: 'candidateUsers'

    }));

    let roleOptions = []
    let roleList = window.localStorage.getItem('roleList')
    if (roleList) {
      roleList = JSON.parse(roleList)
    }
    if (roleList != null) {
      roleList.forEach(item => {
        "use strict";
        roleOptions.push({
          value: item.id,
          name: item.name
        })
      })
    }
    // Candidate Groups
    group.entries.push(entryFactory.multipleBox({
      id: 'candidateGroups',
      label: translate('Candidate Groups'),
      modelProperty: 'candidateGroups',
      selectOptions: roleOptions
    }));

    // Due Date
    group.entries.push(entryFactory.dateFiled({
      id: 'dueDate',
      description: translate('The due date as an EL expression (e.g. ${someDate} or an ISO date (e.g. 2015-06-26T09:54:00)'),
      label: translate('Due Date'),
      modelProperty: 'dueDate'
    }));

    // FollowUp Date
    group.entries.push(entryFactory.textField({
      id: 'followUpDate',
      description: translate('The follow up date as an EL expression (e.g. ${someDate} or an ' +
        'ISO date (e.g. 2015-06-26T09:54:00)'),
      label: translate('Follow Up Date'),
      modelProperty: 'followUpDate'
    }));

    // priority
    group.entries.push(entryFactory.textField({
      id: 'priority',
      label: translate('Priority'),
      modelProperty: 'priority'
    }));

  }
};
