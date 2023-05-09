'use strict';

var escapeHTML = require('../Utils').escapeHTML;

var domQuery = require('min-dom').query;

var entryFieldDescription = require('./EntryFieldDescription');
var defaultButtonAction = function (element, inputNode) {
  var input = domQuery('input[name="' + options.modelProperty + '"]', inputNode);
  input.value = '';

  return true;
};

var tabField = function (options, defaultParameters) {

  // Default action for the button next to the input-field
  var defaultButtonAction = function (element, inputNode) {
    var input = domQuery('input[name="' + options.modelProperty + '"]', inputNode);
    input.value = '';

    return true;
  };

  // default method to determine if the button should be visible
  var defaultButtonShow = function (element, inputNode) {
    var input = domQuery('input[name="' + options.modelProperty + '"]', inputNode);

    return input.value !== '';
  };


  var resource = defaultParameters,
    label = options.label || resource.id,
    dataValueLabel = options.dataValueLabel,
    buttonLabel = (options.buttonLabel || 'X'),
    actionName = (typeof options.buttonAction != 'undefined') ? options.buttonAction.name : 'clear',
    actionMethod = (typeof options.buttonAction != 'undefined') ? options.buttonAction.method : defaultButtonAction,
    showName = (typeof options.buttonShow != 'undefined') ? options.buttonShow.name : 'canClear',
    showMethod = (typeof options.buttonShow != 'undefined') ? options.buttonShow.method : defaultButtonShow,
    canBeDisabled = !!options.disabled && typeof options.disabled === 'function',
    canBeHidden = !!options.hidden && typeof options.hidden === 'function',
    description = options.description;

  resource.html =
    '<label for="camunda-' + resource.id + '" ' +
    (canBeDisabled ? 'data-disable="isDisabled" ' : '') +
    (canBeHidden ? 'data-show="isHidden" ' : '') +
    (dataValueLabel ? 'data-value="' + dataValueLabel + '"' : '') + '>' + label + '</label>' +
    '<div class="bpp-field-wrapper" ' +
    (canBeDisabled ? 'data-disable="isDisabled"' : '') +
    (canBeHidden ? 'data-show="isHidden"' : '') +
    '>' +
    '<div class="left-input-disabled">' +
    '<input id="camunda-' + resource.id + '" type="text" name="' + options.modelProperty + '" ' +
    (canBeDisabled ? 'data-disable="isDisabled"' : '') +
    (canBeHidden ? 'data-show="isHidden"' : '') +
    '  style="display:none;" />' +
    '<button class="' + actionName + '" data-action="' + actionName + '" data-show="' + showName + '" ' +
    (canBeDisabled ? 'data-disable="isDisabled"' : '') +
    (canBeHidden ? ' data-show="isHidden"' : '') +
    ' style="display:none;" ' +
    '>' +
    '<span>' + buttonLabel + '</span>' +
    '</button>' +
    '</div>' +
    '<input type="button" class="btn-select" value="选择" onclick="Open' + resource.id + 'Diag(this,\'' + resource.id + '\')" />' +  //点击方法、
    '<br />' +
    // '<span id="tagBox_' + resource.id + '" style="padding: 0em 0;font-size: 12px;line-height: 1em;display: block;" >' +
    // '</span>'+
    '<div id="tagBox_' + resource.id + '"  style="padding: 0em 0;font-size: 12px;line-height: 1em;display: block;">' +
    '</div>'
  '</div>';


  // add description below text input entry field
  if (description) {
    resource.html += entryFieldDescription(description);
  }

  resource[actionName] = actionMethod;
  resource[showName] = showMethod;

  if (canBeDisabled) {
    resource.isDisabled = function () {
      return options.disabled.apply(resource, arguments);
    };
  }

  if (canBeHidden) {
    resource.isHidden = function () {
      return !options.hidden.apply(resource, arguments);
    };
  }

  resource.cssClasses = ['bpp-textfield'];

  return resource;
};

module.exports = tabField;
