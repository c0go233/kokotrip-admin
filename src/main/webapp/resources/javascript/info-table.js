






// const eInfoTableTbody = $(htmlTag.tbody + '#info-table-tbody');
// const eInfoFormModal = $(htmlTag.div + htmlAttr.id + 'info-form-modal');
// const eInfoSupportLanguageSelect = $(htmlTag.select + htmlAttr.id + 'info-support-language-select');
//
//
// //info inputs
// const eInfoIdInput = $(htmlTag.input + '#info-id-input');
// const eInfoSnippetTextarea = $(htmlTag.textarea + '#info-snippet-textarea');
// const eInfoNameInput = $(htmlTag.input + '#info-name-input');
// const eInfoEnabledCheckbox = $(htmlTag.input + '#info-enabled-checkbox');
// const eInfoUrlPrefixInput = $(htmlTag.input + '#info-url-prefix-input');
//
// //error spans
// const eInfoErrorDescription = $(htmlTag.span + '#info-error-snippet');
// const eInfoErrorException = $(htmlTag.span + '#info-error-exception');
// const eInfoErrorName = $(htmlTag.span + '#info-error-name');


//datatable data index
// const infoCellIndex = {
//     supportLanguageName: 0,
//     name: 1,
//     snippet: 2,
//     enabled: 3,
//     id: 6,
//     supportLanguageId: 7,
//     createdAt: 8,
//     updatedAt: 9
// };


// $(document).ready(function () {
//
//     setListenersToInfoElements();
// });
//
// function setListenersToInfoElements() {
//     $('button#info-form-submit-btn').on(eventType.click, onClickInfoFormSubmitBtn);
//
//     eInfoFormModal.on('hidden.bs.modal', clearInfoForm);
//     eInfoTableTbody.on(eventType.click, 'button.info-row__edit-btn', onClickInfoEditBtn);
//     eInfoTableTbody.on(eventType.click, 'button.info-row__detail-btn', onClickInfoDetailBtn);
// }
//
// function onClickInfoFormSubmitBtn() {
//     $.ajax({
//         url: eInfoUrlPrefixInput.val() + '/info/save',
//         data: $('form#info-form').serialize(),
//         method: ajaxMethod.post,
//         success: populateInfoRow,
//         error: populateInfoErrors
//     });
// }

// function onClickInfoEditBtn() {
//     let data = eInfoDataTable.row($(this).closest(htmlTag.tr)).data();
//     eInfoSupportLanguageSelect.children('option[value=' + data[infoCellIndex.supportLanguageId] + ']').prop(htmlAttr.selected, true);
//     eInfoSupportLanguageSelect.attr(htmlAttr.disabled, htmlAttr.disabled);
//     populateInfoForm(data);
//     eInfoFormModal.modal(htmlAttr.show);
// }



// function clearInfoForm() {
//     clearInfoFormErrors();
//     clearInfoFormFields();
//     eInfoSupportLanguageSelect.removeAttr(htmlAttr.disabled);
// }
//
// function clearInfoFormFields() {
//     eInfoIdInput.val(null);
//     eInfoNameInput.val("");
//     eInfoSnippetTextarea.val("");
//     eInfoEnabledCheckbox.prop('checked', false).change();
// }
//
// function clearInfoFormErrors() {
//     eInfoErrorName.text("");
//     eInfoErrorException.text("");
//     eInfoErrorDescription.text("");
// }
//
//
//
// function populateInfoRow(info) {
//     let row = eInfoDataTable.row('[data-info-id=' + info.id + ']');
//     if (row.length) updateInfoRow(row, info);
//     else createInfoRow(info);
//     eInfoFormModal.modal(htmlAttr.hide);
// }
//
// function updateInfoRow(row, info) {
//     let data = row.data();
//     data[infoCellIndex.name] = info.name;
//     data[infoCellIndex.snippet] = info.snippet;
//     data[infoCellIndex.enabled] = info.enabled;
//     data[infoCellIndex.updatedAt] = info.updatedAt;
//     eInfoDataTable.row('[data-info-id=' + info.id + ']').data(data).draw();
// }
//
// function createInfoRow(info) {
//
//     let node = eInfoDataTable.row.add([
//         info.supportLanguageName,
//         info.name,
//         info.snippet,
//         info.enabled,
//         '<button type="button" class="btn btn-primary info-row__edit-btn">편집</button>',
//         '<button type="button" class="btn btn-primary info-row__detail-btn">상세</button>',
//         info.id,
//         info.supportLanguageId
//
//     ]).node();
//
//     $(node).attr(htmlAttr.dataInfoId, info.id);
//     eInfoDataTable.draw();
// }
//
// function populateInfoErrors(jqXHR) {
//     clearInfoFormErrors();
//     console.log(jqXHR);
//     populateFieldErrors(jqXHR, 'info-error');
// }
//
//
//
// function populateInfoForm(data) {
//     eInfoIdInput.val(data[infoCellIndex.id]);
//     eInfoNameInput.val(data[infoCellIndex.name]);
//     eInfoSnippetTextarea.val(data[infoCellIndex.snippet]);
//     eInfoEnabledCheckbox.prop(htmlAttr.checked, stringToBoolean(data[infoCellIndex.enabled])).change();
// }

// function onClickInfoDetailBtn() {
//
//     let data = eInfoDataTable.row($(this).closest(htmlTag.tr)).data();
//     $('span#info-detail-support-language-name-span').text(data[infoCellIndex.supportLanguageName]);
//     $('span#info-detail-id-span').text(data[infoCellIndex.id]);
//     $('span#info-detail-name-span').text(data[infoCellIndex.name]);
//     $('span#info-detail-snippet-span').text(data[infoCellIndex.snippet]);
//     $('span#info-detail-enabled-span').text(data[infoCellIndex.enabled]);
//     $('span#info-detail-created-at-span').text(data[infoCellIndex.createdAt]);
//     $('span#info-detail-updated-at-span').text(data[infoCellIndex.updatedAt]);
//     $('div#info-detail-modal').modal(htmlAttr.show);
// }


// function onClickInfoDeleteBtn(id) {
//     let row = eInfoTableTbody.children('[data-info-id=' + id + ']');
//     // if (row.length <= 0 || row.attr(htmlAttr.dataInfoId).val().length <= 0)
//     $.ajax({
//         url: eInfoUrlPrefixInput.val() + '/info/delete',
//         data: { id: row.attr(htmlAttr.dataInfoId)},
//         method: ajaxMethod.post,
//         success: removeInfoRow,
//         error: populateDeleteConfirmModalError
//     });
// }

// function setDeleteConfirmModal() {
//     let row = $(this).closest(htmlTag.tr);
//     let supportLanguageName = row.children().eq(infoCellIndex.supportLanguageName).text();
//     let name = row.children().eq(infoCellIndex.name).text();
//     let id = row.attr(htmlAttr.dataInfoId);
//     let statement = supportLanguageName + ' 로 작성된 번역정보: "' + name + '"';
//     openDeleteConfirmModal('여행지 번역', statement, 'onClickInfoDeleteBtn(' + id + ')');
// }


// function removeInfoRow(response) {
//     let row = eInfoTableTbody.children('[data-info-id=' + response.infoId + ']');
//     row.remove();
//     closeDeleteConfirmModal();
// }
