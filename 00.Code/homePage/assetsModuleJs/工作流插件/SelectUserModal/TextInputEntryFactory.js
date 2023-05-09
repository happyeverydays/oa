'use strict';

var escapeHTML = require('../Utils').escapeHTML;

var domQuery = require('min-dom').query;

var entryFieldDescription = require('./EntryFieldDescription');

var textField = function (options, defaultParameters) {

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
  if (resource.id == "candidateStarterUsers") {
    var _box = $('<div></div>');
    var _label = $('<label for="camunda-' + resource.id + '" ' + (canBeDisabled ? 'data-disable="isDisabled" ' : '') + (canBeHidden ? 'data-show="isHidden" ' : '') + (dataValueLabel ? 'data-value="' + dataValueLabel + '"' : '') + '>' + label + '</label>');

    var _div = $('<div class="bpp-field-wrapper" ' + (canBeDisabled ? 'data-disable="isDisabled"' : '') + (canBeHidden ? 'data-show="isHidden"' : '') + '></div>');

    var _div_div = $('<div class="left-input-disabled"></div>');

    var _div_div_input = $('<input type="text" id="camunda-' + resource.id + '"   readonly="readonly" name="' + options.modelProperty + '" ' + (canBeDisabled ? 'data-disable="isDisabled"' : '') + (canBeHidden ? 'data-show="isHidden"' : '') + ' />');

    var _div_div_label = $('<label id="lbl-' + resource.id + '" name="lbl-' + resource.id + '"></label>');

    var _div_div_button = $('<button class="' + actionName + '" data-action="' + actionName + '" data-show="' + showName + '" ' + (canBeDisabled ? 'data-disable="isDisabled"' : '') +
      (canBeHidden ? ' data-show="isHidden"' : '') + '>' + '</button>');

    var _div_div_button_span = $('<span>' + '</span>');
    // var _div_div_input_sel = $('<input type="button" class="btn-select" value="选择" onclick="OpenUserDiag(this)" />');
 
    var _div_div_input_sel = $('<input type="button" class="btn-select" value="选择" onclick="OpenUserDiag(this)" />');
    // var _div_tags=$('<div id="divTags" ></div>');

    var _div_tags=$('<span id="tagBox-list" style="padding: 0em 0;font-size: 12px;line-height: 1em;display: block;"></span>')

    resource.html = _box.append(_label).append(_div.append(_div_div.append(_div_div_input).append(_div_div_label).append(_div_div_button.append(_div_div_button_span))).append(_div_div_input_sel).append(_div_tags)).html();

  } else {
    resource.html =
      '<label for="camunda-' + escapeHTML(resource.id) + '" ' +
      (canBeDisabled ? 'data-disable="isDisabled" ' : '') +
      (canBeHidden ? 'data-show="isHidden" ' : '') +
      (dataValueLabel ? 'data-value="' + escapeHTML(dataValueLabel) + '"' : '') + '>' + escapeHTML(label) + '</label>' +
      '<div class="bpp-field-wrapper" ' +
      (canBeDisabled ? 'data-disable="isDisabled"' : '') +
      (canBeHidden ? 'data-show="isHidden"' : '') +
      '>' +
      '<input id="camunda-' + escapeHTML(resource.id) + '" type="text" name="' + escapeHTML(options.modelProperty) + '" ' +
      (canBeDisabled ? 'data-disable="isDisabled"' : '') +
      (canBeHidden ? 'data-show="isHidden"' : '') +
      ' />' +
      '<button class="' + escapeHTML(actionName) + '" data-action="' + escapeHTML(actionName) + '" data-show="' + escapeHTML(showName) + '" ' +
      (canBeDisabled ? 'data-disable="isDisabled"' : '') +
      (canBeHidden ? ' data-show="isHidden"' : '') + '>' +
      '<span>' + escapeHTML(buttonLabel) + '</span>' +
      '</button>' +
      '</div>';
  }

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


module.exports = textField;
