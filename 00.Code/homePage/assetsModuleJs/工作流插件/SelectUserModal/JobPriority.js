'use strict';

var entryFactory = require('../../../../factory/EntryFactory');

var cmdHelper = require('../../../../helper/CmdHelper');

module.exports = function(element, bpmnFactory, options, translate) {
 
  var getBusinessObject = options.getBusinessObject;

  // var jobPriorityEntry = entryFactory.textField({
  //   id: 'jobPriority',
  //   label: translate('Job Priority'),
  //   modelProperty: 'jobPriority',

  //   get: function(element, node) {
  //     var bo = getBusinessObject(element); 
  //     return {
  //       jobPriority: bo.get('camunda:jobPriority')
  //     };
  //   },

  //   set: function(element, values) {
  //     var bo = getBusinessObject(element);
  //     return cmdHelper.updateBusinessObject(element, bo, {
  //       'camunda:jobPriority': values.jobPriority || undefined
  //     });
  //   }

  // });


    var jobPriorityEntry = entryFactory.selectBox({
    id: 'jobPriority',
    label: translate('Job Priority'),
    modelProperty: 'jobPriority',  
    selectOptions:[ { name: '1', value: '1' },{ name: '2', value: '2' },{ name: '3', value: '3' }],

    get: function(element, node) {
      var bo = getBusinessObject(element); 
      return {
        jobPriority: bo.get('camunda:jobPriority')
      };
    },

    set: function(element, values) {
      var bo = getBusinessObject(element);
      return cmdHelper.updateBusinessObject(element, bo, {
        'camunda:jobPriority': values.jobPriority || undefined
      });
    }

  });


  return [ jobPriorityEntry ];

};
