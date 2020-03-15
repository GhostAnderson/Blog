
function ajax(data,url)
{
    $.ajax(
        {
            type: "post",
            url: url,
            data: data,
            headers: headers,
            contentType: false,
            processData: false,
            success: function (r) {
                var rtn = eval(r);
                if (rtn['errno'] == 500) {
                    alert("操作失败");
                } else if (rtn['errno'] == 200) {
                    alert("操作成功");
                } else {
                    alert(rtn);
                }
            }
        }
    );
}


$('.label-primary').hide();
(function () {
    console.log(currentPage);
    console.log(totalPage);

    if(currentPage == 1)
    {
        $('.lac').addClass("disabled");
    }
    if(currentPage == totalPage)
    {
        $('.rac').addClass("disabled");
    }
})();

function a(e) {
    e = $(e);
    e.before('<div class="label label-success editable" contenteditable="true">ROLE_</div>\n');
    e.prev().focus();
    $('.editable').keydown(function (e) {
        if(e.keyCode == 13)
        {
            $('.editable').blur();
            e.preventDefault();
        }
    });

}

function d(t) {
    t = $(t);
    if (t.text() == "删除用户") {
        t.removeClass('btn-warning');
        t.addClass('btn-danger');
        t.text("确认删除");
    } else if (t.text() == "确认删除")
    {
        var data = new FormData();
        data.append('username',t.next().attr('name'));
        ajax(data,'/admin/deleteuser');
        $('#'+t.next().attr('name')).remove();
    }
}

function b(i) {
    i=$(i);
    if(i.text() == "封禁！")
    {
        i.removeClass('btn-warning');
        i.addClass('btn-danger');
        i.text("确定封禁");
    }
    else if(i.text() == "确定封禁")
    {
        var data = new FormData();
        data.append("username",i.prev().prev().attr('name'));
        ajax(data,'/admin/banuser');
        i.removeClass('btn-danger');
        i.addClass('btn-warning');
        i.text("解封！");
    }
    else if(i.text() == "解封！")
    {
        i.removeClass('btn-warning');
        i.addClass('btn-danger');
        i.text("确定解封");
    }
    else if(i.text() == "确定解封")
    {
        var data = new FormData();
        data.append("username",i.prev().prev().attr('name'));
        ajax(data,'/admin/unbanuser');
        i.removeClass('btn-danger');
        i.addClass('btn-warning');
        i.text("封禁！");
    }
}

function k(i) {
    if($(i).hasClass('disabled'))
        return;
    if (i.textContent == "编辑权限") {
        $(i).prev().addClass('disabled');
        $(i).next().removeClass('disabled');
        console.log(i);
        var username = i.name;
        $('#'+username+' .label-primary').show()
        console.log(username);
        i.textContent = "取消编辑";
        $('#' + username + ' div div').removeClass('dis');
        var _1 = $('#' + username + ' div div').filter('.label-default');
        _1.addClass('label-danger');
        _1.addClass('pointer-cursor');
        _1.removeClass('label-default');
    }
    else if(i.textContent == "取消编辑"){
        $(i).next().addClass('disabled');
        $(i).prev().removeClass('disabled');

        var username = i.name;
        i.textContent = "编辑权限";
        $('#'+username+' .label-primary').hide()

        $('.selected-delete').show();
        $('.selected-delete').removeClass('selected-delete');

        var _1 = $('#' + username + ' div div').filter('.label-danger');
        $('#' + username + ' .label-success').remove();

        _1.addClass('label-default');
        _1.addClass('dis');
        _1.removeClass('pointer-cursor');
        _1.removeClass('label-danger');
    }
}

function s(i) {
    i = $(i);
    if(i.hasClass('disabled'))
        return;
    else
    {
        var username = i.parents().filter('.tr').attr('id');
        var form = new FormData();
        var rolelist = new Array();
        for (var index=0;index<$('#'+username+' .auth-container div').not('.selected-delete').length;index++)
        {
            rolelist[index] = $('#'+username+' .auth-container div').not('.selected-delete')[index].textContent;
            console.log(rolelist[index]);
        }
        form.append('username',username);
        form.append('rolelist',rolelist);
        ajax(form,'/admin/privmanage');
        console.log($('.selected-delete'));
        $('.selected-delete').remove();
        var username = i.prev().attr('name');
        var _1 = $('#' + username + ' div div').filter('.label-danger');
        _1.addClass('label-default');
        _1.addClass('dis');
        _1.removeClass('pointer-cursor');
        _1.removeClass('label-danger');
        i.addClass('disabled');
        i.prev().text('编辑权限');
        $('#'+username+' .label-primary').hide();
        $('#'+username+' .label-success').addClass('label-default');
        $('#'+username+' .label-success').removeClass('label-success');
        i.prev().prev().removeClass('disabled');
    }
}

function j(i) {
    if (i.getAttribute('class').indexOf('dis')!=-1)
    {
        console.log(i);
        return;
    }
    else
    {
        if(!$(i).hasClass('label-primary')) {
            $(i).addClass("selected-delete");
            $(i).hide();
        }
        else
            alert("新增权限");
        console.log(i.getAttribute('name')+' remove '+i.textContent);
    }
}

function nextPage() {
    if($('.rac').hasClass("disabled"))
        return;
    $(".right-container").load('/admin/usermanage?page='+(currentPage+1))
}
function prevPage() {
    if($('.lac').hasClass("disabled"))
        return;
    $(".right-container").load('/admin/usermanage?page='+(currentPage-1))
}