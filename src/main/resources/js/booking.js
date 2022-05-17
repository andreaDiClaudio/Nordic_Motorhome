const changeSelected = (e) => {
    const $select = document.querySelector('#mySelect');
    $select.value = 'steve'
};

document.querySelector('.changeSelected').addEventListener('click', changeSelected);