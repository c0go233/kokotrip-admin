const htmlAttr = {
    id: '#',
    class: '.',
    name: 'name',
    toggle: 'toggle',
    dataId: 'data-id',
    disabled: 'disabled',
    dataDayId: 'data-day-Id',
    dataDayOfWeekId: 'data-day-of-week-id',
    dataOpenTime: 'data-open-time',
    dataTradingHourTypeId: 'data-trading-hour-type-id',
    checked: 'checked',
    selected: 'selected',
    firstChild: 'first-child',
    nThChild: 'nth-child',
    onclick: 'onclick',
    hide: 'hide',
    show: 'show',
    dataInfoId: 'data-info-id',
    dataName: 'data-name',
    dataType: 'data-type',
    dataUrl: 'data-url',
    inactive: 'inactive',
    dataUrlPrefix: 'data-url-prefix',
    open: 'open',
    dataSuccessCallback: 'data-success-callback',
    dataDeleteUrl: 'data-delete-url',
    dataDismiss: 'data-dismiss'
}

const ajaxMethod = {
    post: 'POST',
    get: 'GET'
}

const eventType = {
    click: 'click',
    change: 'change',
    submit: 'submit'
}

const tradingHourType = {
    open: 1,
    close: 2
}

// let token = $("meta[name='_csrf']").attr("content");
// let header = $("meta[name='_csrf_header']").attr("content");
//
//
// $(document).ajaxSend(function(e,xhr,options) {
//     xhr.setRequestHeader(header, token);
// });

$(document).ready(function () {
    setListenerToElements()
    
})

//============================COMMON============================================================//

function setListenerToElements () {
    $('input[type=checkbox]').on(eventType.change, onChangeCheckbox)
    resetCheckbox()
    setListenersToDropdown()
    setListenerToSideNavDropdown()
    setListenerToModalCloseBtn()
    
}

function resetCheckbox () {
    $('input[type=checkbox]').each(function () {
        let value = $(this).val()
        let checked = $(this).is(':checked').toString()
        if (value !== checked) $(this).trigger(eventType.click)
    })
}

function setListenerToModalCloseBtn () {
    $(document).on(eventType.click, 'button[data-dismiss=modal]', function () {
        $(this).parents('div.modal').removeClass(htmlAttr.show)
    })
    
    $(document).on(eventType.click, 'div.modal', function (event) {
        const modalContent = $(event.target).parents('div.modal-content')
        if (modalContent.length <= 0) $(this).removeClass(htmlAttr.show)
    })
}

function onChangeCheckbox () {
    let val = this.checked ? 'true' : 'false'
    
    $(this).val(val)
    $(this).parent().siblings('label.checkbox-label').text(val)
}

function populateFieldErrors (jqXHR, prefix) {
    $.each(jqXHR.responseJSON, function (key, value) {
        $('span#' + prefix + '-' + key).text(value)
    })
}

function setListenerToSideNavDropdown () {
    let sideNav = $('nav#side-nav')
    if (sideNav.length > 0) {
        sideNav.on(eventType.click, 'button.side-nav__dropdown-btn', function () {
            $(this).parent('div.side-nav__dropdown').toggleClass('dropdown-open')
        })
    }
}

// ================================================ K-DROPDOWN ===================================================

function setListenersToDropdown () {
    
    $(document).on(eventType.click, 'div.dropdown__select-wrapper', onClickDropdownSelect)
    $(document).on(eventType.click, clearAllDropdown)
    $(document).on(eventType.click, clearAllAutoCompleteDropdown)
    $(document).on(eventType.click, 'div.dropdown__option-wrapper > button.dropdown__item', onClickDropdownOption)
    
}

function clearAllAutoCompleteDropdown () {
    $('div.auto-complete-wrapper').removeClass(htmlAttr.open)
}

function clearAllDropdown () {
    $('div.k-dropdown').each(function () {
        $(this).removeClass(htmlAttr.open)
    })
}

function onClickDropdownSelect (event) {
    let dropdown = $(this).parent()
    let toggle = $(dropdown).hasClass(htmlAttr.open) ? '' : htmlAttr.open
    clearAllDropdown()
    if (!$(dropdown).hasClass(htmlAttr.inactive)) $(dropdown).addClass(toggle)
    event.stopPropagation()
}

function onClickDropdownOption () {
    $(this).siblings('button[aria-selected=true]').attr('aria-selected', false)
    $(this).attr('aria-selected', true)
    
    let value = $(this).find('.dropdown__item__text').text()
    let select = $(this).parent('.dropdown__option-wrapper').siblings('.dropdown__select-wrapper')
    $(select).find('input').val($(this).attr(htmlAttr.dataId))
    $(select).find('span').text(value)
}

function getSelectedOptionId (dropdown) {
    return dropdown.find('div.dropdown__select-wrapper').find('input').val()
}

// ================================================ TAB LIST ===================================================

function setListenerToTabList () {
    $('ul.tab-list').on(eventType.click, 'button.tab-list__tab', function () {
        $(this).siblings('button.tab-list__tab').removeClass('tab_selected')
        $(this).addClass('tab_selected')
        
        let tabId = $(this).attr('data-target-id')
        $('div.tab-content').each(function () {
            $(this).hide()
        })
        
        $('div#' + tabId).show()
        $('div#' + tabId).find('table.k-table').DataTable().columns.adjust()
    })
    
    selectTab()
}

function selectTab (tabId) {
    //by default the first element selected
    $('ul.tab-list button.tab-list__tab:first-child').trigger(eventType.click)
}

// ============================================ DATA TABLE ======================================================

function getDataTableColumns (urlPrefix, includeDescription, includeOrder) {
    let columns = [
        { 'data': 'id', 'searchable': false },
        { 'data': 'name' }
    ]
    if (includeDescription) columns.push({ 'data': 'description' })
    if (includeOrder) columns.push({ 'data': 'order' })
    columns.push({ 'data': 'enabled' })
    columns.push(getDataTableLinkColumn('편집', urlPrefix + '/edit'))
    columns.push(getDataTableLinkColumn('상세', urlPrefix + '/detail'))
    return columns
}

function getDataTableLinkColumn (label, urlPrefix) {
    return {
        'data': 'id',
        'render': function (data, type, full, meta) {
            return '<a href="' + urlPrefix + '\/' + data + '" class="link-primary">' + label + '</a>'
        },
        'searchable': false,
        'orderable': false
    }
}

function getDataTableColumnDefs (includeDescription, includeOrder) {
    
    let columnDefs = []
    
    if (includeDescription) {
        columnDefs.push({ targets: 0, width: '15%' })
        columnDefs.push({ targets: 1, width: '15%' })
        columnDefs.push({ targets: 2, width: '40%' })
    }
    //
    // let nameWidth = includeDescription ? '15%' : '40%';
    // columnDefs.push({ targets: 1, width: nameWidth });
    // if (includeDescription) columnDefs.push({ targets: 2, width: '40%' });
    
    let editColumn = 3 + (includeDescription ? 1 : 0) + (includeOrder ? 1 : 0)
    let detailColumn = 4 + (includeDescription ? 1 : 0) + (includeOrder ? 1 : 0)
    
    columnDefs.push({ targets: editColumn, searchable: false, orderable: false })
    columnDefs.push({ targets: detailColumn, searchable: false, orderable: false })
    
    return columnDefs
}

function getBasicDataTableSetting (serverSide, pageLength) {
    return {
        'processing': true,
        'serverSide': serverSide,
        'bLengthChange': false,
        'info': false,
        'dom': '<"toolbar">frtip',
        'pageLength': pageLength,
        'language': {
            search: '',
            searchPlaceholder: '검색어를 입력하세요',
            processing: '<div class="lds-ellipsis"><div></div><div></div><div></div><div></div></div>',
            emptyTable: '조회된 내용이 없습니다'
        }
    }
}

function setDataTable (dataTableId,
    serverSide,
    pageLength,
    baseUrl,
    prefixUrl,
    addUrl,
    includeDescription,
    includeOrder) {
    let datatableSetting = getBasicDataTableSetting(serverSide, pageLength)
    datatableSetting.columnDefs = getDataTableColumnDefs(includeDescription, includeOrder)
    
    if (serverSide) datatableSetting.columns = getDataTableColumns(baseUrl + prefixUrl, includeDescription, includeOrder)
    if (serverSide) datatableSetting.ajax = {
        'url': baseUrl + prefixUrl + '/list/paginated',
        'type': 'GET',
        'dataType': 'json'
    }
    
    let table = $('table#' + dataTableId)
    table.DataTable(datatableSetting)
    appendAddLinkToDataTableToolbar(table, baseUrl + prefixUrl + addUrl)
    
}

function setTourSpotDataTable (baseUrl, prefixUrl) {
    let table = $('table#tour-spot-data-table')
    let datatableSetting = getBasicDataTableSetting(true, 5)
    let columns = []
    columns.push({ 'data': 'id' })
    columns.push({ 'data': 'name' })
    columns.push({ 'data': 'description' })
    columns.push({ 'data': 'enabled' })
    columns.push({ 'data': 'city.name' })
    columns.push({ 'data': 'region.name' })
    columns.push(getDataTableLinkColumn('편집', baseUrl + prefixUrl + '/edit'))
    columns.push(getDataTableLinkColumn('상세', baseUrl + prefixUrl + '/detail'))
    
    datatableSetting.columns = columns
    
    let columnDefs = []
    columnDefs.push({ targets: 0, width: '15%' })
    columnDefs.push({ targets: 1, width: '15%' })
    columnDefs.push({ targets: 2, width: '30%' })
    columnDefs.push({ targets: 3, width: '8%' })
    columnDefs.push({ targets: 4, width: '10%' })
    columnDefs.push({ targets: 5, width: '10%' })
    columnDefs.push({ targets: 6, width: '8%' })
    columnDefs.push({ targets: 7, width: '8%' })
    
    datatableSetting.columnDefs = columnDefs
    datatableSetting.ajax = { 'url': baseUrl + prefixUrl + '/list/paginated', 'type': 'GET', 'dataType': 'json' }
    
    table.DataTable(datatableSetting)
    appendAddLinkToDataTableToolbar(table, baseUrl + prefixUrl + '/add')
    
}

function setActivityDataTable (baseUrl, prefixUrl) {
    let table = $('table#activity-data-table')
    let datatableSetting = getBasicDataTableSetting(true, 5)
    let columns = []
    columns.push({ 'data': 'id' })
    columns.push({ 'data': 'name' })
    columns.push({ 'data': 'description' })
    columns.push({ 'data': 'tag.name' })
    columns.push({ 'data': 'enabled' })
    columns.push({ 'data': 'tourSpot.name' })
    columns.push(getDataTableLinkColumn('편집', baseUrl + prefixUrl + '/edit'))
    columns.push(getDataTableLinkColumn('상세', baseUrl + prefixUrl + '/detail'))
    
    datatableSetting.columns = columns
    
    let columnDefs = []
    columnDefs.push({ targets: 0, width: '15%' })
    columnDefs.push({ targets: 1, width: '15%' })
    columnDefs.push({ targets: 2, width: '30%' })
    columnDefs.push({ targets: 3, width: '15%' })
    columnDefs.push({ targets: 4, width: '10%' })
    columnDefs.push({ targets: 5, width: '15%' })
    columnDefs.push({ targets: 6, width: '8%' })
    columnDefs.push({ targets: 7, width: '8%' })
    
    datatableSetting.columnDefs = columnDefs
    datatableSetting.ajax = { 'url': baseUrl + prefixUrl + '/list/paginated', 'type': 'GET', 'dataType': 'json' }
    
    table.DataTable(datatableSetting)
    appendAddLinkToDataTableToolbar(table, baseUrl + prefixUrl + '/add')
}

function appendAddLinkToDataTableToolbar (dataTable, addUrl) {
    let addTag = '<a class="link-primary tool-bar__add-link" href="' + addUrl + '">' +
        '<div class="cross-primary"></div>' +
        '<span> 추가하기</span>' +
        '</a>'
    dataTable.siblings('div.toolbar').html(addTag)
}

//============================ DELETE MODAL ==============================================//

let eDeleteConfirmModal;
let eDeleteConfirmBtn;
let eDeleteConfirmModalStatement;
let eDeleteConfirmModalTitlePrefix;
let eDeleteConfirmModalErrorException;

function setDeleteConfirmModal () {
    eDeleteConfirmModal = $('div#delete-confirm-modal');
    eDeleteConfirmBtn = $('button#delete-confirm-btn');
    eDeleteConfirmModalStatement = $('span#delete-confirm-modal-statement');
    eDeleteConfirmModalTitlePrefix = $('span#delete-confirm-modal-title-prefix');
    eDeleteConfirmModalErrorException = $('span#delete-confirm-modal-error-exception');
    eDeleteConfirmBtn.on(eventType.click, onClickDeleteConfirmBtn);
}

function onClickDeleteConfirmBtn () {
    $.ajax({
        url: $(this).attr(htmlAttr.dataDeleteUrl),
        data: { id: $(this).attr(htmlAttr.dataId) },
        dataType: 'json',
        method: ajaxMethod.post,
        success: window[$(this).attr(htmlAttr.dataSuccessCallback)],
        error: populateDeleteConfirmModalError
    })
}

function onClickDetailDeleteBtn () {
    openDeleteConfirmModal($(this).attr(htmlAttr.dataId),
        $(this).attr(htmlAttr.dataName),
        $(this).attr(htmlAttr.dataType),
        $(this).attr(htmlAttr.dataDeleteUrl),
        $(this).attr(htmlAttr.dataSuccessCallback))
}

function openDeleteConfirmModal (id, name, type, url, successCallback) {
    eDeleteConfirmModalTitlePrefix.text(type);
    eDeleteConfirmModalStatement.text(name);
    eDeleteConfirmBtn.attr(htmlAttr.dataId, id);
    eDeleteConfirmBtn.attr(htmlAttr.dataDeleteUrl, url);
    eDeleteConfirmBtn.attr(htmlAttr.dataSuccessCallback, successCallback);
    eDeleteConfirmModal.toggleClass(htmlAttr.show);
    eDeleteConfirmModalErrorException.text('');
    
}

function redirectToUrl (response) {
    let result = response.result;
    if (result.startsWith('redirect:')) {
        let url = result.substr(9);
        window.location.replace(url);
    }
}

function populateDeleteConfirmModalError (jqXHR) {
    populateFieldErrors(jqXHR, 'delete-confirm-modal-error');
}

//========================== INFO FUNCTIONS ==============================================//

//info table
let eInfoTableTbody;
let eInfoDataTable;
let defaultSupportLanguageId = 2;

const infoCellIndex = {
    id: 0,
    supportLanguageName: 1,
    name: 2,
    description: 3,
    supportLanguageId: 7,
    tagName: 8,
    createdAt: 9,
    updatedAt: 10,
}

//info form
let eInfoFormModal;
let eInfoSupportLanguageSelect;
let eInfoIdInput;
let eInfoDescriptionTextarea;
let eInfoNameInput;
let eInfoErrorDescription;
let eInfoErrorException;
let eInfoErrorName;

//info detail
let eInfoDetailModal;
let eInfoDetailSupportLanguageNameSpan;
let eInfoDetailIdSpan;
let eInfoDetailNameSpan;
let eInfoDetailDescriptionSpan;
let eInfoDetailCreatedAtSpan;
let eInfoDetailUpdatedAtSpan;
let eInfoDetailTagSpan;

function setInfoTable (includeDescription) {
    let datatableSetting = getBasicDataTableSetting(false, 10);
    datatableSetting.columnDefs = getInfoDataTableColumnDefs(includeDescription);
    eInfoDataTable = $('table#info-data-table').DataTable(datatableSetting);
    eInfoTableTbody = $('tbody#info-table-tbody');
    
    appendAddBtnToToolbar();
    setListenersToInfoTableElements();
}

function appendAddBtnToToolbar () {
    let addTag = '<button id="info-add-btn" class="link-primary k-btn tool-bar__add-link"><div class="cross-primary"></div><span>추가하기</span></button>';
    $('table#info-data-table').siblings('div.toolbar').html(addTag);
}

function setListenersToInfoTableElements () {
    eInfoTableTbody.on(eventType.click, 'button.info-row__edit-btn', onClickInfoEditBtn)
    eInfoTableTbody.on(eventType.click, 'button.info-row__detail-btn', onClickInfoDetailBtn)
    eInfoTableTbody.on(eventType.click, 'button.info-row__delete-btn', function () {
        let data = eInfoDataTable.row($(this).closest('tr')).data()
        let name = data[infoCellIndex.supportLanguageName] + '로 작성된 번역정보: ' + data[infoCellIndex.name]
        openDeleteConfirmModal(data[infoCellIndex.id],
            name,
            '번역정보',
            eInfoTableTbody.attr('data-delete-url'),
            eInfoTableTbody.attr(htmlAttr.dataSuccessCallback))
    });
    
    $('button#info-add-btn').on(eventType.click, function () {
        openInfoForm(null, '', '', defaultSupportLanguageId)
        eInfoSupportLanguageSelect.removeClass(htmlAttr.inactive)
    })
}

function onClickInfoFormSubmitBtn () {
    
    $.ajax({
        url: $(this).attr(htmlAttr.dataUrlPrefix) + '/info/save',
        data: $('form#info-form').serialize(),
        method: ajaxMethod.post,
        dataType: 'json',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        // contentType: 'application/json',
        success: populateInfoRow,
        error: populateInfoErrors
    })
}

function populateInfoErrors (jqXHR) {
    clearInfoFormErrors()
    populateFieldErrors(jqXHR, 'info-error')
}

function populateInfoRow (info) {
    let row = eInfoDataTable.row('[data-info-id=' + info.id + ']')
    if (row.length) updateInfoRow(row, info)
    else createInfoRow(info)
    eInfoFormModal.removeClass(htmlAttr.show)
}

function createInfoRow (info) {
    let node = eInfoDataTable.row.add([
        info.id,
        info.supportLanguageName,
        info.name,
        info.description,
        '<button type="button" class="btn-light btn-small k-btn info-row__edit-btn">편집</button>',
        '<button type="button" class="btn-light btn-small k-btn  info-row__detail-btn">상세</button>',
        '<button type="button" class="btn-delete btn-small k-btn  info-row__delete-btn">삭제</button>',
        info.supportLanguageId,
        info.tagName,
        info.createdAt,
        info.updatedAt
    
    ]).node()
    
    $(node).attr(htmlAttr.dataInfoId, info.id)
    eInfoDataTable.draw()
}

function updateInfoRow (row, info) {
    let data = row.data();
    data[infoCellIndex.name] = info.name;
    data[infoCellIndex.enabled] = info.enabled;
    data[infoCellIndex.description] = info.description;
    data[infoCellIndex.updatedAt] = info.updatedAt;
    eInfoDataTable.row('[data-info-id=' + info.id + ']').data(data).draw();
}

function removeInfoRow (response) {
    let row = eInfoTableTbody.find('tr[data-info-id=' + response.result + ']');
    eInfoDataTable.row(row).remove().draw();
    eDeleteConfirmModal.removeClass(htmlAttr.show);
}

function onClickInfoEditBtn () {
    let data = eInfoDataTable.row($(this).closest('tr')).data()
    eInfoSupportLanguageSelect.addClass(htmlAttr.inactive)
    openInfoForm(data[infoCellIndex.id], data[infoCellIndex.name],
        data[infoCellIndex.description], data[infoCellIndex.supportLanguageId])
}

function openInfoForm (id, name, description, supportLanguageId) {
    eInfoIdInput.val(id)
    eInfoNameInput.val(name)
    eInfoDescriptionTextarea.val(description)
    eInfoSupportLanguageSelect.find('button.dropdown__item[data-id=' + supportLanguageId + ']').trigger(eventType.click)
    clearInfoFormErrors()
    eInfoFormModal.addClass(htmlAttr.show)
}

function onClickInfoDetailBtn () {
    let data = eInfoDataTable.row($(this).closest('tr')).data()
    eInfoDetailSupportLanguageNameSpan.text(data[infoCellIndex.supportLanguageName])
    eInfoDetailIdSpan.text(data[infoCellIndex.id])
    eInfoDetailNameSpan.text(data[infoCellIndex.name])
    eInfoDetailDescriptionSpan.text(data[infoCellIndex.description])
    eInfoDetailCreatedAtSpan.text(data[infoCellIndex.createdAt])
    eInfoDetailUpdatedAtSpan.text(data[infoCellIndex.updatedAt])
    eInfoDetailTagSpan.text(data[infoCellIndex.tagName])
    eInfoDetailModal.addClass(htmlAttr.show)
}

function clearInfoFormErrors () {
    eInfoErrorName.text('')
    eInfoErrorException.text('')
    eInfoErrorDescription.text('')
}

function setInfoFormModal () {
    eInfoFormModal = $('div#info-form-modal')
    eInfoSupportLanguageSelect = $('div#info-support-language-select')
    eInfoIdInput = $('input#info-id-input')
    eInfoDescriptionTextarea = $('textarea#info-description-textarea')
    eInfoNameInput = $('input#info-name-input')
    eInfoErrorDescription = $('span#info-error-description')
    eInfoErrorException = $('span#info-error-exception')
    eInfoErrorName = $('span#info-error-name')
    $('button#info-form-submit-btn').on(eventType.click, onClickInfoFormSubmitBtn)
}

function setInfoDetailModal () {
    eInfoDetailModal = $('div#info-detail-modal')
    eInfoDetailSupportLanguageNameSpan = $('span#info-detail-support-language-name-span')
    eInfoDetailIdSpan = $('span#info-detail-id-span')
    eInfoDetailNameSpan = $('span#info-detail-name-span')
    eInfoDetailDescriptionSpan = $('span#info-detail-description-span')
    eInfoDetailCreatedAtSpan = $('span#info-detail-created-at-span')
    eInfoDetailUpdatedAtSpan = $('span#info-detail-updated-at-span')
    eInfoDetailTagSpan = $('span#info-detail-tag-span')
}

function getInfoDataTableColumnDefs (includeDescription) {
    let columnDefs = []
    
    if (includeDescription) {
        columnDefs.push({ targets: 0, width: '13%' })
        columnDefs.push({ targets: 1, width: '10%' })
        columnDefs.push({ targets: 2, width: '15%' })
        columnDefs.push({ targets: 3, width: '40%' })
        columnDefs.push({ targets: 4, width: '8%', orderable: false, searchable: false })
        columnDefs.push({ targets: 5, width: '8%', orderable: false, searchable: false })
        columnDefs.push({ targets: 6, width: '8%', orderable: false, searchable: false })
        
    } else columnDefs.push({ targets: 3, visible: includeDescription })
    
    columnDefs.push({ targets: 7, visible: false })
    columnDefs.push({ targets: 8, visible: false })
    columnDefs.push({ targets: 9, visible: false })
    columnDefs.push({ targets: 10, visible: false })
    
    return columnDefs
}

// ========================================================== TOUR_SPOT ========================================== //

let eCityDropdown
let eRegionDropdown
let eTourSpotTimeTableMaker

function setRegionSelect () {
    eCityDropdown = $('div#city-dropdown')
    eRegionDropdown = $('div#region-dropdown')
    eTourSpotTimeTableMaker = $('div#tour-spot-time-table-maker')
    
    eCityDropdown.find('div.dropdown__option-wrapper').on(eventType.click, 'button.dropdown__item', function () {
        resetRegionSelect(true, $(this).attr(htmlAttr.dataId))
    })
}

function resetRegionSelect (selectDefaultOption, cityId) {
    
    let optionCount = 0
    
    eRegionDropdown.find('div.dropdown__option-wrapper').children().each(function () {
        if ($(this).attr('data-city-id') === cityId.toString()) {
            $(this).removeClass(htmlAttr.hide)
            optionCount++
        } else $(this).addClass(htmlAttr.hide)
    })
    
    let defaultOption = eRegionDropdown.find('button#default-option')
    defaultOption.removeClass(htmlAttr.hide)
    
    if (selectDefaultOption) defaultOption.trigger(eventType.click)
    else eRegionDropdown.find('button[aria-selected=true]').trigger(eventType.click)
    
    let emptyOption = eRegionDropdown.find('div.empty-option')
    if (optionCount <= 0) {
        emptyOption.removeClass(htmlAttr.hide)
        defaultOption.addClass(htmlAttr.hide)
    } else emptyOption.addClass(htmlAttr.hide)
    
}

// ========================================================== TIMETABLE BUILDER ============================================== //

let eTimetableBuilderAddBtn
let eTimetableDayOfWeekInput
let eTimetableTradingHourTypeInput
let eTimetableOpenHourInput
let eTimetableOpenMinuteInput
let eTimetableCloseHourInput
let eTimetableCloseMinuteInput
let eTimetableTradingHourWrapper
let eTimetableContainer

function setTimetableBuilder () {
    eTimetableBuilderAddBtn = $('button#timetable-add-btn')
    eTimetableDayOfWeekInput = $('input#timetable-day-of-week-input')
    eTimetableTradingHourTypeInput = $('input#timetable-trading-hour-type-input')
    eTimetableOpenHourInput = $('input#timetable-builder-open-hour-input')
    eTimetableOpenMinuteInput = $('input#timetable-builder-open-minute-input')
    eTimetableCloseHourInput = $('input#timetable-builder-close-hour-input')
    eTimetableCloseMinuteInput = $('input#timetable-builder-close-minute-input')
    eTimetableTradingHourWrapper = $('div#timetable-trading-hour-wrapper')
    eTimetableContainer = $('div#timetable-builder-container')
    
    eTimetableContainer.on(eventType.click, 'div.timetable-builder__trading-hour-type-option-wrapper button.dropdown__item', function () {
        let tradingHourTypeId = parseInt($(this).attr(htmlAttr.dataId))
        
        let row = $(this).parents('div.timetable-builder-wrapper')
        
        if (tradingHourTypeId === tradingHourType.close) {
            row.find('div.timetable-builder__time-dropdown').each(function () {
                $(this).addClass(htmlAttr.inactive)
                $(this).find('button.dropdown__item[data-id="00"]').each(function () {
                    $(this).trigger(eventType.click)
                })
            })
        } else if (tradingHourTypeId === tradingHourType.open) {
            row.find('div.timetable-builder__time-dropdown').each(function () {
                $(this).removeClass(htmlAttr.inactive)
            })
        }
        
    })
    
    eTimetableBuilderAddBtn.on(eventType.click, addTradingHour)
    eTimetableTradingHourWrapper.on(eventType.click, 'button.timetable-builder__delete-btn', function () {
        $(this).parents('div[role=row]').remove()
    })
    
    eTimetableTradingHourWrapper.on(eventType.click, '.dropdown__option-wrapper > button.dropdown__item', onClickDropdownOption)
    eTimetableTradingHourWrapper.on(eventType.click, '.dropdown__time-option-wrapper > button.dropdown__item', function () {
        
        let isOpenTime = $(this).parents('div.dropdown__time-option-wrapper').hasClass('dropdown__open-time-option-wrapper')
        let row = $(this).parents('div[role=row].timetable-builder-wrapper')
        let time = ''
        let input = $(this).parents('div.k-dropdown').siblings('input')
        let multiDropdownWrapper = $(this).parents('div.timetable-builder__multi-dropdown-wrapper')
        $(multiDropdownWrapper).find('div.k-dropdown').each(function () {
            time += $(this).find('input').val() + ':'
        }).promise().done(function () {
            time += '00'
            input.val(time)
            if (isOpenTime) {
                row.attr(htmlAttr.dataOpenTime, time)
                sortTradingHourTable()
            }
        })
    })
    
    eTimetableTradingHourWrapper.on(eventType.click, 'div.dropdown__day-of-week-option-wrapper > button.dropdown__item', function () {
        let value = $(this).attr(htmlAttr.dataId)
        let row = $(this).parents('div[role=row].timetable-builder-wrapper')
        row.attr(htmlAttr.dataDayOfWeekId, value)
        sortTradingHourTable()
    })
    
}

function addTradingHour () {
    let tradingHourRow = getTradingHourRowMarkup()
    eTimetableTradingHourWrapper.append(tradingHourRow)
    sortTradingHourTable()
}

function getTradingHourRowMarkup () {
    let dayOfWeekId = eTimetableDayOfWeekInput.val()
    let tradingHourTypeId = eTimetableTradingHourTypeInput.val()
    let openTime = tradingHourTypeId == tradingHourType.close ? '00:00' : eTimetableOpenHourInput.val() + ':' + eTimetableOpenMinuteInput.val() + ':00'
    let closeTime = tradingHourTypeId == tradingHourType.close ? '00:00' : eTimetableCloseHourInput.val() + ':' + eTimetableCloseMinuteInput.val() + ':00'
    
    let row = $('<div role="row" class="timetable-builder-wrapper" data-day-of-week-id="' + dayOfWeekId + '" data-open-time="' + openTime + '"></div>')
    
    row.append(getDropdownMarkup(eTimetableDayOfWeekInput, 'tradingHourVmList[0].dayOfWeekId'))
    row.append(getDropdownMarkup(eTimetableTradingHourTypeInput, 'tradingHourVmList[0].tradingHourTypeId'))
    row.append(getMultiDropdownMarkup(eTimetableOpenHourInput, openTime, 'tradingHourVmList[0].openTime'))
    row.append(getMultiDropdownMarkup(eTimetableCloseHourInput, closeTime, 'tradingHourVmList[0].closeTime'))
    row.append('<div class="timetable-builder__item"><button type="button" class="timetable-builder__btn timetable-builder__delete-btn btn-close"></button></div>')
    return row
}

function getMultiDropdownMarkup (input, time, inputName) {
    let builderItem = $('<div class="timetable-builder__item"></div>')
    let multiDropdown = input.parents('div.timetable-builder__multi-dropdown-wrapper').clone()
    multiDropdown.find('input').each(function () {
        setInputIdAndName($(this), '', '')
    })
    
    multiDropdown.append('<input type="hidden" name="' + inputName + '" value="' + time + '">')
    builderItem.append(multiDropdown)
    return builderItem
}

function sortTradingHourTable () {
    let switching = true
    let rows, shouldSwitch, i
    
    while (switching) {
        switching = false
        rows = eTimetableTradingHourWrapper.children()
        
        for (i = 0; i < (rows.length - 1); i++) {
            shouldSwitch = false
            if (isNextTradingHourEarlierThanCurrent(rows[i], rows[i + 1])) {
                shouldSwitch = true
                break
            }
        }
        
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i])
            switching = true
        }
    }
}

function isNextTradingHourEarlierThanCurrent (currentRow, nextRow) {
    let currentRowDayOfWeekId = $(currentRow).attr(htmlAttr.dataDayOfWeekId)
    let nextRowDayOfWeekId = $(nextRow).attr(htmlAttr.dataDayOfWeekId)
    
    let currentOpenTime = parseInt($(currentRow).attr(htmlAttr.dataOpenTime).replace(':', ''))
    let nextOpenTime = parseInt($(nextRow).attr(htmlAttr.dataOpenTime).replace(':', ''))
    
    return (nextRowDayOfWeekId <= currentRowDayOfWeekId && nextOpenTime < currentOpenTime)
        || (nextRowDayOfWeekId < currentRowDayOfWeekId)
}

function setListInputsBeforeSubmit (wrapper, inputWrapperClass) {
    wrapper.find(inputWrapperClass).each(function (index) {
        $(this).find('input').each(function () {
            
            let name = $(this).attr(htmlAttr.name);
            let indexOfBracket = name.indexOf('[');
            let left = name.substr(0, (indexOfBracket + 1));
            let right = name.substr(name.indexOf(']'));
            let newName = left + index + right;
            $(this).attr(htmlAttr.name, newName);
        })
    })
}

function setTradingHourInputs () {
    setListInputsBeforeSubmit(eTimetableTradingHourWrapper, 'div.timetable-builder-wrapper')
}

// ========================================================== TICKET PRICE BUILDER ============================================== //

let eTicketPriceBuilderAddBtn;
let eTicketPriceBuilderRepPriceCheckbox;
let eTicketPriceBuilderTicketPriceInput;
let eTicketPriceWrapper;
let eTicketPriceBuilderTicketTypeInput;

function setTicketPriceBuilder () {
    eTicketPriceBuilderAddBtn = $('button#ticket-price-builder-add-btn')
    eTicketPriceBuilderRepPriceCheckbox = $('input#ticket-price-builder-rep-price-checkbox')
    eTicketPriceBuilderTicketPriceInput = $('input#ticket-price-builder-ticket-price-input')
    eTicketPriceWrapper = $('div#ticket-price-wrapper')
    eTicketPriceBuilderTicketTypeInput = $('input#ticket-price-builder-ticket-type-input')
    eTicketPriceBuilderAddBtn.on(eventType.click, addTicketPrice)
    eTicketPriceWrapper.on(eventType.click, 'button.timetable-builder__delete-btn', function () {
        $(this).parents('div[role=row]').remove()
    })
    
    eTicketPriceWrapper.on(eventType.click, 'input[type=checkbox]', selectRepPriceCheckbox)
}

function selectRepPriceCheckbox (event) {
    uncheckCheckbox(eTicketPriceWrapper.find('input[value=true]'))
    checkCheckbox($(this))
    event.stopPropagation()
    
}

function checkCheckbox (checkbox) {
    checkbox.prop(htmlAttr.checked, true)
    checkbox.val('true')
    checkbox.parent().siblings('label.checkbox-label').text('true')
}

function uncheckCheckbox (checkbox) {
    checkbox.prop(htmlAttr.checked, false)
    checkbox.val('false')
    checkbox.parent().siblings('label.checkbox-label').text('false')
}

function addTicketPrice () {
    let row = $('<div role="row" class="timetable-builder-wrapper ticket-price-builder-wrapper"></div>')
    let repPrice = eTicketPriceBuilderRepPriceCheckbox.val()
    
    row.append(getDropdownMarkup(eTicketPriceBuilderTicketTypeInput, 'ticketPriceVmList[0].ticketTypeId'))
    row.append(getTicketPriceInputMarkup(eTicketPriceBuilderTicketPriceInput, 'ticketPriceVmList[0].price'))
    row.append(getCheckboxMarkup(eTicketPriceBuilderRepPriceCheckbox, 'ticketPriceVmList[0].repPrice'))
    row.append('<div class="timetable-builder__item">' +
        '<button type="button" class="timetable-builder__btn timetable-builder__delete-btn btn-close"></button></div>')
    
    if (repPrice === 'true') uncheckCheckbox(eTicketPriceWrapper.find('input[value=true]'))
    eTicketPriceWrapper.append(row)
}

function getTicketPriceInputMarkup () {
    let price = eTicketPriceBuilderTicketPriceInput.val()
    if (price.length === 0) price = '0'
    price = price.includes('.') ? price : price + '.0'
    
    let builderItem = $('<div class="timetable-builder__item"></div>')
    let span = $('<span class="input-currency-won"></span>')
    let newInput = eTicketPriceBuilderTicketPriceInput.clone()
    $(newInput).val(price)
    setInputIdAndName(newInput, '', 'ticketPriceVmList[0].price')
    span.append(newInput)
    builderItem.append(span)
    return builderItem
}

function getDropdownMarkup (input, inputName) {
    let builderItem = $('<div class="timetable-builder__item"></div>')
    let dropdown = input.parents('div.k-dropdown').clone()
    let newInput = $(dropdown).find('div.k-input > input')
    setInputIdAndName(newInput, '', inputName)
    return builderItem.append(dropdown)
}

function getCheckboxMarkup (input, inputName) {
    let builderItem = $('<div class="timetable-builder__item"></div>')
    let checkbox = input.parent('label.switch').parent().clone()
    let newInput = checkbox.find('label.switch > input')
    setInputIdAndName(newInput, '', inputName)
    
    return builderItem.append(checkbox)
}

function setInputIdAndName (input, id, name) {
    input.attr('id', id)
    input.attr('name', name)
}

function setTicketPriceInputs () {
    setListInputsBeforeSubmit(eTicketPriceWrapper, 'div.timetable-builder-wrapper')
}

//============================ AUTO COMPLETE ==============================================//

let tourSpotAutoCompleteUnselectBtn
let activityAutoCompleteUnselectBtn

function setPhotoZoneAutoCompleteSelect () {
    
    tourSpotAutoCompleteUnselectBtn = $('button#tour-spot-auto-complete-unselect-btn')
    activityAutoCompleteUnselectBtn = $('button#activity-auto-complete-unselect-btn')
    
    $('div.activity-auto-complete-option-wrapper').on(eventType.click, 'button.dropdown__option', function () {
        console.log('photozone auto option activity wrapper')
        tourSpotAutoCompleteUnselectBtn.trigger(eventType.click)
    })
    
    $('div.tour-spot-auto-complete-option-wrapper').on(eventType.click, 'button.dropdown__option', function () {
        activityAutoCompleteUnselectBtn.trigger(eventType.click)
    })
}

function setAutoCompleteSelect () {
    
    $('div.auto-complete-wrapper').on(eventType.click, 'button.auto-complete__unselect-btn', function () {
        
        let autoCompleteWrapper = $(this).parents('div.auto-complete-wrapper')
        let autoCompleteInputSelect = autoCompleteWrapper.find('input.auto-complete-select')
        let autoCompleteSelectWrapper = autoCompleteInputSelect.parent()
        let autoCompleteInput = autoCompleteWrapper.find('input.auto-complete-input')
        let autoCompleteOptionBtn = autoCompleteWrapper.find('div.auto-complete__option-wrapper').find('button.dropdown__option:first-child')
        let form = autoCompleteWrapper.parents('form')
        
        autoCompleteInputSelect.prop(htmlAttr.disabled, false)
        autoCompleteInputSelect.val(null)
        autoCompleteSelectWrapper.removeClass('auto-complete_selected')
        autoCompleteInput.val(null)
        
        $.each(autoCompleteOptionBtn.data(), function (key, value) {
            if (key === 'name') return true
            else if (key === 'id') return true
            else form.find('input[name="' + key + '"]').val(null)
        })
        
    })
    
    $('div.auto-complete__option-wrapper').on(eventType.click, 'button.dropdown__option', function () {
        
        let autoCompleteWrapper = $(this).parents('div.auto-complete-wrapper')
        let autoCompleteInputSelect = autoCompleteWrapper.find('input.auto-complete-select')
        let autoCompleteSelectWrapper = autoCompleteInputSelect.parent()
        let autoCompleteInput = autoCompleteWrapper.find('input.auto-complete-input')
        let form = $(this).parents('form')
        
        $.each($(this).data(), function (key, value) {
            if (key === 'name') {
                autoCompleteInputSelect.val(value)
                return true
            } else if (key === 'id') {
                autoCompleteInput.val(value)
                return true
            } else {
                form.find('input[name="' + key + '"]').val(value)
            }
        })
        
        autoCompleteInputSelect.prop(htmlAttr.disabled, true)
        
        if (!autoCompleteSelectWrapper.hasClass('auto-complete_selected'))
            autoCompleteSelectWrapper.addClass('auto-complete_selected')
    })
    
    $('input.auto-complete-select').on('input', function () {
        
        let autoCompleteWrapper = $(this).parents('div.auto-complete-wrapper')
        let autoCompleteOptionWrapper = autoCompleteWrapper.find('div.auto-complete__option-wrapper')
        
        $.ajax({
            'url': $(this).attr('data-url') + '?search=' + $(this).val(),
            'type': 'GET',
            'dataType': 'json',
            'success': function (response) {
                
                autoCompleteOptionWrapper.empty();
                
                let count = 0;
                
                response.forEach(element => {
                    count++;
                    
                    let dataAttributes = '';
                    for (let [key, value] of Object.entries(element)) {
                        dataAttributes += 'data-' + key + '="' + value + '" '
                    }
                    
                    let markup = '<button type="button" class="dropdown__option" role="option" ' + dataAttributes + '>' +
                        '<div>' + element.name + '</div></button>';
                    autoCompleteOptionWrapper.append(markup)
                });
                
                if (count <= 0) {
                    let emptyOptionMarkup = '<div class="empty-option">조회된 내용이 없습니다</div>';
                    autoCompleteOptionWrapper.append(emptyOptionMarkup)
                }
                
                if (!autoCompleteWrapper.hasClass(htmlAttr.open))
                    autoCompleteWrapper.addClass(htmlAttr.open);
                
            },
            'error': function () {
            
            }
        })
        
    })
}

//============================ IMAGE GALLERY ==============================================//

let imageGalleryFileInput;
let imageGalleryAddBtn;
let imageGalleryDeleteBtn;
let imageGalleryRepresentativeBtn;
let imageGalleryDropBox;
let imageGalleryImageList;
let imageGallerySaveBtn;
let dropIndex;

function setImageGallery () {
    imageGalleryFileInput = $('input#image-gallery-file-input');
    imageGalleryAddBtn = $('button#image-gallery-add-btn');
    imageGalleryDeleteBtn = $('button#image-gallery-delete-btn');
    imageGalleryRepresentativeBtn = $('button#image-gallery-representative-image-btn');
    imageGalleryDropBox = $('div.image-gallery__drop-box');
    imageGalleryImageList = $('ul.image-gallery__image-list');
    imageGallerySaveBtn = $('button#image-gallery__save-btn');


    let numOfImage = imageGalleryImageList.find('li.image-gallery__item').length;
    if (numOfImage > 0) imageGalleryDropBox.addClass('filled');
    
    $('div.app-container').on(eventType.click, 'button[data-dismiss=modal]', function () {
        $(this).parents('div.error-popup').remove();
    });
    
    imageGalleryAddBtn.on(eventType.click, function () {
        imageGalleryFileInput.click();
    });
    
    imageGalleryDropBox.on(eventType.click, 'div.image-gallery__image-wrapper', function () {
        let processing = $(this).parents('li.image-gallery__item').hasClass('processing');

        if (!processing) {
            let selected = $(this).hasClass('selected');
            imageGalleryImageList.find('div.image-gallery__image-wrapper').each(function () {
                $(this).removeClass('selected');
            });
    
            if (!selected) $(this).addClass('selected');
        }
    });
    
    imageGalleryDropBox.on(eventType.click, 'button.image-gallery__upload-btn', function () {
        let imageItem = $(this).parents('li.image-gallery__item');
        uploadImage(imageItem, false)
    });
    
    imageGalleryDropBox.on(eventType.click, 'button.image-gallery__download-btn', function () {
        let imageItem = $(this).parents('li.image-gallery__item');
        let sourceImage = imageItem.find('img.source-image');
        let imageUrl = sourceImage.attr('src');
        showImageItem(imageItem, 'image-gallery__image-wrapper');
        imageItem.addClass('processing');
        sourceImage.attr('src', imageUrl);
    });
    
    imageGalleryFileInput.on(eventType.change, function (e) {
        resizeImageAndLoad(e.target.files[0]);
    });

    
    imageGalleryRepresentativeBtn.on(eventType.click, function () {
        let selectedImageWrapper = imageGalleryImageList.find('div.image-gallery__image-wrapper.selected');
        if (selectedImageWrapper.length) {
            let imageItem = selectedImageWrapper.parents('li.image-gallery__item');

            if (!imageItem.hasClass('processing') && !imageItem.hasClass('rep-image')) {
                let imageId = imageItem.attr('data-image-id');
                $.ajax({
                    url: $('meta[name=contextPath]').attr('content') + imageGalleryDropBox.attr('data-prefix-url') + '/rep-image/update',
                    data: {imageId: imageId},
                    dataType: 'json',
                    method: ajaxMethod.post,
                    success: function () {
                        imageGalleryImageList.find('li.image-gallery__item.rep-image').each(function () {
                           $(this).removeClass('rep-image');
                        });
                        imageItem.addClass('rep-image');
                    },
                    error: function (jqXHR) {
                        let exceptionMessage = jqXHR.responseJSON.exception;
                        $('div.app-container').append(createPopup(exceptionMessage, 'ERROR'));

                    }
                });
            }
        } else {
            $('div.app-container').append(createPopup('이미지를 선택해주세요', 'ERROR'));
        }
    });

    imageGalleryImageList.on(eventType.click, 'button.image-gallery__close-btn', function () {
        let imageItem = $(this).parents('li.image-gallery__item');
        if (!imageItem.hasClass('processing')) {
            let imageId = imageItem.attr('data-image-id');

            if (typeof imageId !== 'undefined' && imageId.length) {
                let deleteUrl = $('meta[name=contextPath]').attr('content') + imageGalleryDropBox.attr('data-prefix-url') + '/delete';
                let fileName = imageItem.find('div.image-gallery__caption').text();
                openDeleteConfirmModal(imageId, fileName, '이미지', deleteUrl, 'onSuccessDeleteImage');
            } else {
                imageItem.remove();
                checkIfDropboxEmpty();
            }
        }
    });

    imageGalleryImageList.sortable({
        update: function (event, ui) {
            dropIndex = ui.item.index();
        }
    });

    imageGalleryDropBox.on('drop', function (e, ui) {

        let dataTransfer = e.originalEvent.dataTransfer;

        if (dataTransfer && dataTransfer.files.length) {
            e.preventDefault();
            e.stopPropagation();

            $.each(dataTransfer.files, function (i, file) {
                resizeImageAndLoad(file);
            });
        }
    });

    imageGalleryDropBox.on('dragenter', function (e) {
        e.preventDefault();
        e.stopPropagation();
    });

    imageGalleryDropBox.on('dragover', function (e) {
        e.preventDefault();
        e.stopPropagation();
    });

    imageGallerySaveBtn.on(eventType.click, function () {

        let imageIdList = [];

        let processingExists = false;

        imageGalleryImageList.find('li.image-gallery__item').each(function () {

            let processing = $(this).hasClass('processing');
            if (processing) {
                processingExists = true;
                return false;
            }

            let imageId = $(this).attr('data-image-id');
            if (typeof imageId !== 'undefined' && imageId.length)
                imageIdList.push(parseInt(imageId));
        });

        if (!processingExists) {
            processingAllImage();
            $.ajax({
                url: $('meta[name=contextPath]').attr('content') + imageGalleryDropBox.attr('data-prefix-url') + '/order/save',
                data: JSON.stringify(imageIdList),
                contentType: 'application/json',
                dataType: 'json',
                method: ajaxMethod.post,
                success: function () {
                    unprocessAllImage();
                },
                error: function (jqXHR) {
                    unprocessAllImage();
                    $('div.app-container').append(createPopup("이미지를 저장하지 못했습니다.", 'ERROR'));
                }
            });
        } else {
            $('div.app-container').append(createPopup("프로세싱중인 이미지가 존재합니다", 'ERROR'));
        }


    });

}

function processingAllImage() {
    imageGalleryImageList.find('li.image-gallery__item').each(function () {
       $(this).addClass('processing');
    });
}

function unprocessAllImage() {
    imageGalleryImageList.find('li.image-gallery__item').each(function () {
        $(this).removeClass('processing');
    });
}

function onSuccessDeleteImage(response) {
    imageGalleryImageList.find('li[data-image-id="' + response.result +'"]').remove();
    eDeleteConfirmModal.removeClass('show');
    checkIfDropboxEmpty();
}

function checkIfDropboxEmpty() {
    let numOfImage = imageGalleryImageList.find('li.image-gallery__item').length;
    if (numOfImage <= 0) imageGalleryDropBox.removeClass('filled');
}

function resizeImageAndLoad (file) {
    if (file.type.match('image.*')) {
        let reader = new FileReader();

        imageGalleryDropBox.addClass('filled');

        reader.onload = function (readerEvent) {
            let image = new Image();

            image.onload = function () {

                let canvas = document.createElement('canvas');
                let maxSize = 100;
                let width = image.width;
                let height = image.height;

                width = 100;
                height = 100;

                canvas.width = width;
                canvas.height = height;
                canvas.getContext('2d').drawImage(image, 0, 0, width, height);

                let dataUrl = canvas.toDataURL(file.type);

                if (dataUrl) {
                    let imageItem = createImageItem(file.name, file.type, dataUrl);
                    uploadImage($(imageItem), true);
                }
            };
            image.src = readerEvent.target.result;
        };
        reader.readAsDataURL(file);
    }
}




function uploadImage (imageItem, appendToImageList) {

    let sourceImageTag = imageItem.find('img.source-image');
    let data = new FormData();

    let image = dataURLToBlob(sourceImageTag.attr('src'));
    let fileName = imageItem.find('div.image-gallery__caption').text();
    let repImage = imageItem.hasClass('rep-image');
    let imageOrder = getImageOrder();

    imageItem.addClass('processing');
    imageItem.attr('data-image-order', imageOrder);
    showImageItem(imageItem, 'image-gallery__image-wrapper');
    if (appendToImageList) imageGalleryImageList.append(imageItem);


    data.append('image', image);
    data.append('fileName', fileName);
    data.append(imageGalleryDropBox.attr('data-owner-id-name'), imageGalleryDropBox.attr('data-owner-id'));
    data.append('order', imageOrder);
    data.append('repImage', repImage);

    $.ajax({
        url: $('meta[name=contextPath]').attr('content') + imageGalleryDropBox.attr('data-prefix-url') + '/save',
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
        success: function (response) {
            imageItem.attr('data-image-id', response.result);
            imageItem.removeClass('processing');
        },
        error: function (jqXHR) {
            imageItem.removeClass('processing');
            showImageItem(imageItem, 'image-gallery__upload-error-wrapper');
            let exceptionMessage = jqXHR.responseJSON.exception;
            $('div.app-container').append(createPopup(exceptionMessage, 'ERROR'));
        }
    })
}

function getImageOrder() {
    let order = 0;
    imageGalleryImageList.find('li.image-gallery__item').each(function () {
        let imageOrder = $(this).attr('data-image-order');

        if (typeof imageOrder !== 'undefined' ) {
            let imageOrderInt = parseInt(imageOrder);
            if (imageOrderInt > order) {
                order = imageOrderInt;
            }
        }
    });
    order++;
    return order.toString();
}


function repImageExists() {
    if (imageGalleryDropBox.find('li.image-gallery__item.rep-image').length) return true;
    return false;
}


function showImageItem (imageItem, classToOpen) {
    imageItem.children().each(function () {
        if ($(this).hasClass(classToOpen)) $(this).removeClass('hide');
        else $(this).addClass('hide');
    })
}

function createPopup (message, title) {
    return '<div id="delete-confirm-modal" class="modal fade error-popup" tabindex="-1" role="dialog">' +
        '    <div class="modal-dialog modal-dialog-centered" role="document">' +
        '        <div class="modal-content">' +
        '            <div class="modal-header">' +
        '                <h5 class="modal-title">' + title + '</h5>' +
        '                <button type="button" class="close k-btn" data-dismiss="modal" aria-label="Close">' +
        '                    <img src="' + $('meta[name=contextPath]').attr('content') + '/resources/image/close.png" class="icon-small"/>' +
        '                </button>' +
        '            </div>' +
        '            <div class="modal-body delete-confirm-modal-body">' +
        '                <div>' + message + '</div>' +
        '            </div>' +
        '            <div class="modal-footer">' +
        '                <button type="button" class="btn-bold btn-large btn " data-dismiss="modal">닫기</button>' +
        '            </div>' +
        '        </div>' +
        '    </div>' +
        '</div>';
}

function createImageItem (fileName, fileType, dataUrl) {
    let repImageClass = repImageExists() ? '' : 'rep-image';
    return '<li class="image-gallery__item ' + repImageClass + '">' +
        '    <div class="image-gallery__image-wrapper">' +
        '        <div class="lds-ellipsis">' +
        '            <div></div>' +
        '            <div></div>' +
        '            <div></div>' +
        '            <div></div>' +
        '        </div>' +
        '        <div class="screen-filter"></div>' +
        '        <button class="btn-close image-gallery__close-btn"></button>' +
        '        <div class="image-gallery__check-icon-wrapper">' +
        '            <img src="' + $('meta[name=contextPath]').attr('content') + '/resources/image/round-check-primary.png"' +
        '                 class="image-gallery__check-icon"/>' +
        '        </div>' +
        '        <div class="image-gallery__caption">' + fileName + '</div>' +
        '        <img src="' + dataUrl + '" class="source-image" >' +
        '    </div>' +
        '    <div class="image-gallery__error-wrapper image-gallery__upload-error-wrapper hide">' +
        '        <button class="btn-close image-gallery__close-btn"></button>' +
        '        <img src="' + $('meta[name=contextPath]').attr('content') + '/resources/image/image.png">' +
        '        <p>업로드를 실패했습니다.</p>' +
        '        <button type="button" class="image-gallery__upload-btn k-btn btn-bold image-gallery__btn">' +
        '            재업로드' +
        '        </button>' +
        '    </div>' +
        '    <div class="image-gallery__error-wrapper image-gallery__download-error-wrapper hide">' +
        '        <button class="btn-close image-gallery__close-btn"></button>' +
        '        <img src="' + $('meta[name=contextPath]').attr('content') + '/resources/image/image.png">' +
        '        <p>다운로드를 실패했습니다.</p>' +
        '        <button type="button" class="image-gallery__download-btn k-btn btn-bold image-gallery__btn">' +
        '            다운로드' +
        '        </button>' +
        '    </div>' +
        '</li>';
}

let dataURLToBlob = function (dataURL) {
    let BASE64_MARKER = ';base64,';
    if (dataURL.indexOf(BASE64_MARKER) == -1) {
        let parts = dataURL.split(',');
        let contentType = parts[0].split(':')[1];
        let raw = parts[1];
        
        return new Blob([raw], { type: contentType });
    }
    
    let parts = dataURL.split(BASE64_MARKER);
    let contentType = parts[0].split(':')[1];
    let raw = window.atob(parts[1]);
    let rawLength = raw.length;
    
    let uInt8Array = new Uint8Array(rawLength);
    
    for (let i = 0; i < rawLength; ++i) {
        uInt8Array[i] = raw.charCodeAt(i);
    }
    
    let blob = new Blob([uInt8Array], { type: contentType });
    
    console.log(blob);
    return blob;
}
