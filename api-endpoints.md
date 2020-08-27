# API 接口

- [通用数据返回格式](#通用数据返回格式)
  - [非分页操作成功](#非分页操作成功)
  - [分页操作成功](#分页操作成功)
  - [操作失败](#操作失败)
  - [参数校验失败](#参数校验失败)
  - [状态码](#状态码)
- [用户](#用户)
  - [用户登录](#用户登录)
  - [用户退出登录](#用户退出登录)
  - [用户注册](#用户注册)
  - [发送邮件验证码](#发送邮件验证码)
  - [用户找回密码](#用户找回密码)
  - [用户查看指定用户的个人中心](#用户查看指定用户的个人中心)
  - [用户修改个人资料](#用户修改个人资料)
  - [用户修改头像](#用户修改头像)
  - [用户修改密码](#用户修改密码)
  - [用户激活邮箱](#用户激活邮箱)
  - [用户修改邮箱](#用户修改邮箱)
  - [获取用户在指定板块的权限](#获取用户在指定板块的权限)
  - [【管理】查看用户列表](#管理查看用户列表)
  - [【管理】查看用户名列表](#管理查看用户名列表)
  - [【管理】添加用户](#管理添加用户)
  - [【管理】删除用户](#管理删除用户)
  - [【管理】设置版主](#管理设置版主)
  - [【管理】设置分区版主](#管理设置分区版主)
  - [【管理】添加超级版主](#管理添加超级版主)
  - [【管理】删除超级版主](#管理删除超级版主)
  - [【管理】修改用户的论坛权限](#管理修改用户的论坛权限)
  - [【管理】修改用户的板块权限](#管理修改用户的板块权限)
  - [【管理】修改用户资料](#管理修改用户资料)
- [主题帖](#主题帖)
  - [查看指定板块的主题帖列表](#查看指定板块的主题帖列表)
  - [查看指定用户的主题帖列表](#查看指定用户的主题帖列表)
  - [【管理】查看所有主题帖列表](#管理查看所有主题帖列表)
  - [查看指定主题帖详细信息](#查看指定主题帖详细信息)
  - [发布主题帖](#发布主题帖)
  - [编辑主题帖](#编辑主题帖)
  - [【管理】主题帖管理（单个+批量）](#管理主题帖管理单个批量)
  - [发帖者删除主题帖](#发帖者删除主题帖)
  - [查看指定主题帖的操作记录](#查看指定主题帖的操作记录)
- [回复帖](#回复帖)
  - [查看指定主题帖的回复帖](#查看指定主题帖的回复帖)
  - [查看指定用户的回复帖列表](#查看指定用户的回复帖列表)
  - [【管理】查看所有回复帖列表](#管理查看所有回复帖列表)
  - [发表回复帖](#发表回复帖)
  - [编辑回复帖](#编辑回复帖)
  - [【管理】批量删除回复帖](#管理批量删除回复帖)
  - [发帖者删除回复帖](#发帖者删除回复帖)
- [附件、图片](#附件图片)
  - [【管理】查看附件列表](#管理查看附件列表)
  - [上传图片](#上传图片)
  - [上传附件](#上传附件)
  - [【管理】删除附件](#管理删除附件)
- [板块](#板块)
  - [【管理】查看所有板块](#管理查看所有板块)
  - [【管理】查看板块名称列表](#管理查看板块名称列表)
  - [【管理】创建板块](#管理创建板块)
  - [【管理】编辑板块](#管理编辑板块)
  - [【管理】删除板块](#管理删除板块)
- [分区](#分区)
  - [【管理】查看所有分区](#管理查看所有分区)
  - [【管理】查看分区名称列表](#管理查看分区名称列表)
  - [【管理】创建分区](#管理创建分区)
  - [【管理】编辑分区](#管理编辑分区)
  - [【管理】删除分区](#管理删除分区)
- [友情链接](#友情链接)
  - [查看所有友情链接](#查看所有友情链接)
  - [【管理】添加友情链接](#管理添加友情链接)
  - [【管理】编辑友情链接](#管理编辑友情链接)
  - [【管理】删除友情链接](#管理删除友情链接)
- [操作记录](#操作记录)
  - [查看全站操作记录列表](#查看全站操作记录列表)
- [验证码](#验证码)
  - [获取验证码](#获取验证码)
- [其他](#其他)
  - [论坛主页获取分区和板块信息](#论坛主页获取分区和板块信息)

---

## 通用数据返回格式

### 非分页操作成功

```json5
{
    "code": 200, // 状态码，一定为200
    "data": { ... }, // 操作成功时返回对象，可以是对象、列表、null
    "msg": "success", // 提示信息
    "errorData": null // 参数校验错误信息
}
```

### 分页操作成功

只要请求类型为 `GET` 且参数含有 `page` 和 `count` 的都为分页查询

```json5
{
    "code": 200, // 状态码
    "data": {
        "content": [ ... ], // 操作成功时返回对象，若无值则返回空列表
        "currentPage": 2, // 当前页码
        "pageSize": 1, // 每页结果个数
        "totalPages": 2, // 总共页数
        "totalRecords": 2, // 总共结果数
        "hasPrevious": true, // 是否有上一页
        "hasNext": false // 是否有下一页
    },
    "msg": "success", // 提示信息
    "errorData": null // 参数校验错误信息
}
```

### 操作失败

```json5
{
    "code": 10000, // 状态码，值非200，只拿来判断操作是否成功
    "data": null, // 操作成功时返回对象，一定为null
    "msg": "找不到该用户", // 提示信息，直接弹出来就行
    "errorData": null // 参数校验错误信息
}
```

### 参数校验失败

```json5
{
    "code": 400, // 状态码，一定为400
    "data": null, // 操作成功时返回对象，一定为null
    "msg": "参数校验失败", // 提示信息
    "errorData": { // 参数校验错误信息
        "username": [ // 字段名
            "用户名重复", // 错误信息
            "xxxxx"
        ],
        "email": [
            "邮箱格式不正确"
        ]
    }
}
```

### 状态码

| 状态码 | 含义                  |
| ------ | --------------------- |
| 200    | 请求成功              |
| 400    | 参数校验失败          |
| 401    | 用户没有权限          |
| 403    | 用户未登录            |
| 500    | 内部服务器错误        |
| 其他   | 业务错误，弹 msg 即可 |

## 用户

### 用户登录

`POST /api/user/login`

#### 权限

无

#### 参数

```json5
{
    "username": "a", // 用户名，必填，最大30字符，[A-Za-z0-9_]
    "password": "aaaaaa", // 密码，必填，6-20位
    "verifyCode": "abcd", // 图形验证码，必填，4位
    "verifyCodeRandom": "sOdQceeE" // 获取验证码时的随机字符串
}
```

#### 操作成功时返回对象

```json5
{
    "id": 1, // 用户编号
    "username": "a", // 用户名
    "nickname": "a", // 昵称
    "avatarPath": "/api/file/avatar/xxx.jpg", // 头像地址
    "permission": { // 权限
        "boardAdmin": true, // 是否有管理的板块
        "categoryAdmin": true, // 是否有管理的分区
        "superBoardAdmin": false, // 是否为超级版主
        "admin": false // 是否为管理员
    }
}
```

### 用户退出登录

`POST /api/user/logout`

#### 权限

无

#### 参数

无

#### 操作成功时返回对象

```json5
null
```

### 用户注册

`POST /api/user/register`

#### 权限

无

#### 参数

```json5
{
    "username": "a", // 用户名，必填，最大30字符，[A-Za-z0-9_]
    "password": "aaaaaa", // 密码，必填，6-20位
    "nickname": "a", // 昵称，必填，最大30字符
    "email": "a@a.cn", // 邮箱，必填，后端校验
    "verifyCode": "abcd", // 图形验证码，必填，4位
    "verifyCodeRandom": "sOdQceeE" // 获取验证码时的随机字符串
}
```

#### 操作成功时返回对象

```json5
null
```

### 发送邮件验证码

`POST /api/user/send-email-verify-code`

#### 权限

无

#### 参数

```json5
{
    "email": "a@a.cn", // 邮箱，为null的话表示当前登录用户的邮箱
    "verifyCode": "abcd", // 图形验证码，邮箱不为null的时候必填，4位
    "verifyCodeRandom": "sOdQceeE" // 获取验证码时的随机字符串
}
```

#### 操作成功时返回对象

```json5
null
```

### 用户找回密码

`POST /api/user/reset-password`

#### 权限

无

#### 参数

```json5
{
    "emailVerifyCode": "abcdef", // 邮箱验证码，必填，6位
    "newPassword": "aaaaaa", // 新密码，必填，6-20位
    "verifyCode": "abcd", // 图形验证码，必填，4位
    "verifyCodeRandom": "sOdQceeE" // 获取验证码时的随机字符串
}
```

#### 操作成功时返回对象

```json5
null
```

### 用户查看指定用户的个人中心

`GET /api/user/user-info?userId=`

#### 权限

无

#### 参数

| 字段名 | 字段类型 | 必填 | 含义     | 样例 |
| ------ | -------- | ---- | -------- | ---- |
| userId | int      | 是   | 用户编号 | 1    |

#### 操作成功时返回对象

```json5
{
    "id": 1, // 用户编号
    "username": "a", // 用户名
    "nickname": "a", // 昵称
    "superBoardAdmin": true, // 是否为超级版主
    "admin": true, // 是否为管理员
    "avatarPath": "/api/file/avatar/xxx.jpg", // 头像地址
    "email": "a@a.cn", // 邮箱【自己的个人中心才显示】
    "emailVerified": true, // 邮箱是否已激活【自己的个人中心才显示】
    "sex": 1, // 性别，0-男，1-女，2-保密
    "signature": "aaaasdsada", // 个人签名
    "topicCount": 1, // 主题帖总数
    "replyCount": 1, // 回复总数
    "registerTime": "2020-08-02 17:30:00", // 注册时间
    "boardAdmin": [ // 管理的板块
        {
            "id": 1, // 板块编号
            "name": "板块 #1" // 板块名称
        },
        {
            "id": 2,
            "name": "板块 #2"
        }
    ],
    "categoryAdmin": [ // 管理的分区
        {
            "id": 1, // 分区编号
            "name": "分区 #1" // 分区名称
        },
        {
            "id": 2,
            "name": "分区 #2"
        }
    ]
}
```

### 用户修改个人资料

`POST /api/user/update-info`

#### 权限

已登录用户

#### 参数

```json5
{
    "nickname": "a", // 昵称，最大30字符
    "sex": 1, // 性别，0-男，1-女，2-保密
    "signature": "sadasdsa" // 个人签名，最大500字符
}
```

#### 操作成功时返回对象

```json5
null
```

### 用户修改头像

`POST /api/user/update-avatar`

#### 权限

已登录用户

#### 参数

头像文件，支持jpg/png，最低100x100，最高500x500，宽高比为1:1

#### 操作成功时返回对象

```json5
{
    "avatarPath": "/api/file/avatar/xxx.jpg" // 头像地址
}
```

### 用户修改密码

`POST /api/user/update-password`

#### 权限

已登录用户

#### 参数

```json5
{
    "oldPassword": "abcdef", // 旧密码，必填，6-20位
    "newPassword": "aaaaaa", // 新密码，必填，6-20位
    "emailVerifyCode": "abcdef" // 邮箱验证码，必填，6位
}
```

#### 操作成功时返回对象

```json5
null
```

### 用户激活邮箱

`POST /api/user/verify-email`

#### 权限

已登录用户

#### 参数

```json5
{
    "emailVerifyCode": "abcdef" // 邮箱验证码，必填，6位
}
```

#### 操作成功时返回对象

```json5
null
```

### 用户修改邮箱

`POST /api/user/update-email`

#### 权限

已登录用户

#### 参数

```json5
{
    "oldEmailVerifyCode": "abcdef", // 旧邮箱的验证码，必填，6位
    "newEmail": "b@a.cn", // 新邮箱地址，必填，后端校验
    "newEmailVerifyCode": "abcdef" // 新邮箱的验证码，必填，6位
}
```

#### 操作成功时返回对象

```json5
null
```

### 获取用户在指定板块的权限

`GET /api/user/board-permission?boardId=`

> 在进入板块时获取，用来修改对应按钮的可见性

#### 权限

已登录用户

#### 参数

| 字段名  | 字段类型 | 必填 | 含义     | 样例 |
| ------- | -------- | ---- | -------- | ---- |
| boardId | int      | 是   | 板块编号 | 1    |

#### 操作成功时返回对象

```json5
{
    "boardAdmin": true, // 是否为当前板块的版主
    "banVisit": false, // 禁止访问
    "banCreateTopic": false, // 禁止创建主题帖
    "banReply": false, // 禁止回复
    "banUploadAttachment": false, // 禁止上传附件
    "banDownloadAttachment": false // 禁止下载附件
}
```

### 【管理】查看用户列表

`GET /api/user/user-list?username=&type=&page=&count=`

#### 权限

管理员

#### 参数

| 字段名   | 字段类型 | 必填 | 含义                   | 样例  |
| -------- | -------- | ---- | ---------------------- | ----- |
| username | string   | 否   | 按用户名精确筛选       | admin |
| userType | string   | 否   | 按用户类型筛选         | user  |
| page     | int      | 是   | 页码                   | 1     |
| count    | int      | 是   | 一次获取的个数，上限20 | 20    |

用户类型：

| userType        | 含义                   |
| --------------- | ---------------------- |
| user            | 普通会员               |
| boardAdmin      | 版主                   |
| categoryAdmin   | 分区版主               |
| superBoardAdmin | 超级版主               |
| admin           | 管理员                 |
| banVisit        | 禁止访问（登录）的用户 |
| banReply        | 禁止发帖、回帖的用户   |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 用户编号
        "username": "a", // 用户名
        "nickname": "a", // 昵称
        "superBoardAdmin": true, // 是否为超级版主
        "admin": true, // 是否为管理员
        "avatarPath": "/api/file/avatar/xxx.jpg", // 头像地址
        "email": "a@a.cn", // 邮箱
        "emailVerified": true, // 邮箱是否已激活
        "sex": 1, // 性别，0-男，1-女，2-保密
        "topicCount": 1, // 主题帖总数
        "replyCount": 1, // 回复总数
        "signature": "a", // 个人签名
        "lastLoginTime": "2020-08-02 17:30:00", // 上次登录时间
        "lastLoginIp": "111.111.111.111", // 上次登录IP
        "userType": "管理员", // 用户类型（字符串）
        "boardAdmin": [ // 管理的板块
            {
                "id": 1, // 板块编号
                "name": "板块 #1" // 板块名称
            },
            {
                "id": 2,
                "name": "板块 #2"
            }
        ],
        "categoryAdmin": [ // 管理的分区
            {
                "id": 1, // 分区编号
                "name": "分区 #1" // 分区名称
            },
            {
                "id": 2,
                "name": "分区 #2"
            }
        ],
        "registerTime": "2020-08-02 17:30:00", // 注册时间
        "forumPermission": { // 论坛权限
            "banVisit": false, // 禁止访问（登录）
            "banCreateTopic": false, // 禁止创建主题帖
            "banReply": false, // 禁止回复
            "banUploadAttachment": false, // 禁止上传附件
            "banDownloadAttachment": false // 禁止下载附件
        },
        "boardPermission": [ // 板块权限
            {
                "id": 1, // 板块编号
                "name": "板块 #1", // 板块名称
                "banVisit": false, // 禁止访问
                "banCreateTopic": false, // 禁止创建主题帖
                "banReply": false, // 禁止回复
                "banUploadAttachment": false, // 禁止上传附件
                "banDownloadAttachment": false // 禁止下载附件
            },
            {
                "id": 2,
                "name": "板块 #2",
                "banVisit": false,
                "banCreateTopic": false,
                "banReply": false,
                "banUploadAttachment": false,
                "banDownloadAttachment": false
            }
        ]
    }
]
```

### 【管理】查看用户名列表

> 此接口用于补全完整用户名

`GET /api/user/username-list?user=`

#### 权限

管理员

#### 参数

| 字段名 | 字段类型 | 必填 | 含义              | 样例  |
| ------ | -------- | ---- | ----------------- | ----- |
| user   | string   | 否   | 按用户名/昵称搜索 | admin |

#### 操作成功时返回对象

只返回10个

```json5
[
    {
        "id": 1, // 用户编号
        "username": "a", // 用户名
        "nickname": "a", // 昵称
        "deleted": true // 是否已被删除
    },
    {
        "id": 2,
        "username": "b",
        "nickname": "b",
        "deleted": false
    }
]
```

### 【管理】添加用户

`POST /api/user/add-user`

#### 权限

管理员

#### 参数

```json5
{
    "username": "a", // 用户名，必填，最大30字符，[A-Za-z0-9_]
    "password": "aaaaaa", // 密码，必填，6-20位
    "nickname": "a", // 昵称，必填，最大30字符
    "email": "a@a.cn", // 邮箱，必填，后端校验
    "admin": false, // 是否为管理员，必填
    "forumPermission": { // 论坛权限，必填
        "banVisit": false, // 禁止访问（登录）
        "banCreateTopic": false, // 禁止创建主题帖
        "banReply": false, // 禁止回复
        "banUploadAttachment": false, // 禁止上传附件
        "banDownloadAttachment": false // 禁止下载附件
    }
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】删除用户

`POST /api/user/delete-user`

#### 权限

管理员

#### 参数

```json5
{
    "id": 1 // 用户编号
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】设置版主

`POST /api/user/update-board-admin`

#### 权限

管理员

#### 参数

```json5
{
    "userId": 1, // 用户编号
    "boardIdList": [ // 板块编号列表
        1,
        2,
        3
    ]
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】设置分区版主

`POST /api/user/update-category-admin`

#### 权限

管理员

#### 参数

```json5
{
    "userId": 1, // 用户编号
    "categoryIdList": [ // 分区编号列表
        1,
        2,
        3
    ]
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】添加超级版主

`POST /api/user/add-super-board-admin`

#### 权限

管理员

#### 参数

```json5
{
    "userId": 1 // 用户编号
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】删除超级版主

`POST /api/user/delete-super-board-admin`

#### 权限

管理员

#### 参数

```json5
{
    "userId": 1 // 用户编号
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】修改用户的论坛权限

`POST /api/user/update-forum-permission`

#### 权限

管理员

#### 参数

```json5
{
    "userId": 1, // 用户编号
    "banVisit": false, // 禁止访问（登录）
    "banCreateTopic": false, // 禁止创建主题帖
    "banReply": false, // 禁止回复
    "banUploadAttachment": false, // 禁止上传附件
    "banDownloadAttachment": false // 禁止下载附件
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】修改用户的板块权限

`POST /api/user/update-board-permission`

#### 权限

管理员

#### 参数

```json5
{
    "userId": 1, // 用户编号
    "boardId": 1, // 板块编号
    "banVisit": false, // 禁止访问
    "banCreateTopic": false, // 禁止创建主题帖
    "banReply": false, // 禁止回复
    "banUploadAttachment": false, // 禁止上传附件
    "banDownloadAttachment": false // 禁止下载附件
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】修改用户资料

`POST /api/user/update-user-info`

#### 权限

管理员

#### 参数

```json5
{
    "id": 4, // 用户编号
    "username": "a", // 用户名，必填，最大30字符，[A-Za-z0-9_]
    "password": "aaaaaa", // 密码，6-20位，为空字符串表示不更改
    "nickname": "a", // 昵称，必填，最大30字符
    "email": "a@a.cn", // 邮箱，必填，后端校验
    "admin": true, // 是否为管理员，必填
    "sex": 0, // 性别，0-男，1-女，2-保密，必填
    "signature": "a" // 个人签名，必填，最大500字符
}
```

#### 操作成功时返回对象

```json5
null
```

## 主题帖

### 查看指定板块的主题帖列表

`GET /api/topic/board-topic-list?boardId=&type=&sort=&page=&count=`

#### 权限

有访问权限的所有用户

#### 参数

| 字段名  | 字段类型 | 必填 | 含义                              | 样例      |
| ------- | -------- | ---- | --------------------------------- | --------- |
| boardId | int      | 是   | 板块编号                          | 1         |
| type    | string   | 否   | 按主题帖类型筛选，默认为 `normal` | normal    |
| sort    | string   | 否   | 排序方式，默认为 `replyTime`      | replyTime |
| page    | int      | 是   | 页码                              | 1         |
| count   | int      | 是   | 一次获取的个数，上限20            | 20        |

主题帖类型：

| type         | 含义              |
| ------------ | ----------------- |
| normal       | 普通主题+精华主题 |
| featured     | 精华主题          |
| pinned       | 置顶主题          |
| announcement | 公告主题          |
| all          | 全部主题          |

排序方式：

| sort       | 含义         |
| ---------- | ------------ |
| replyTime  | 最后回复时间 |
| submitTime | 发帖时间     |
| viewCount  | 浏览次数     |
| replyCount | 回复数       |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 主题帖编号
        "type": 0, // 主题类型，0-普通主题，1-公告
        "title": "a", // 标题
        "shortContent": "a a [图片] eeee", // 短内容，纯文字
        "images": [ // 图片列表（最多三张）
            "http://example.com/1.jpg",
            "http://example.com/2.jpg",
            "http://example.com/3.jpg"
        ],
        "submitTime": "2020-08-04 16:00:00", // 发帖时间
        "submitterUserId": 1, // 发帖人用户编号
        "submitterNickname": "a", // 发帖人昵称
        "submitterAvatarPath": "/api/file/avatar/xxx.jpg", // 发帖人头像地址
        "viewCount": 1, // 浏览次数
        "replyCount": 1, // 回复数
        "lastReplyTime": "2020-08-04 16:00:00", // 最后回复时间
        "lastReplierUserId": 1, // 最后回复者用户编号
        "lastReplierNickname": "a", // 最后回复者昵称
        "pinned": false, // 是否置顶
        "featured": false // 是否精华
    }
]
```

### 查看指定用户的主题帖列表

`GET /api/topic/user-topic-list?userId=&type=&sort=&page=&count=`

#### 权限

无

#### 参数

| 字段名 | 字段类型 | 必填 | 含义                              | 样例      |
| ------ | -------- | ---- | --------------------------------- | --------- |
| userId | int      | 是   | 用户编号                          | 1         |
| type   | string   | 否   | 按主题帖类型筛选，默认为 `normal` | normal    |
| sort   | string   | 否   | 排序方式，默认为 `replyTime`      | replyTime |
| page   | int      | 是   | 页码                              | 1         |
| count  | int      | 是   | 一次获取的个数，上限20            | 20        |

主题帖类型：

| type         | 含义              |
| ------------ | ----------------- |
| normal       | 普通主题+精华主题 |
| featured     | 精华主题          |
| pinned       | 置顶主题          |
| announcement | 公告主题          |
| all          | 全部主题          |

排序方式：

| sort       | 含义         |
| ---------- | ------------ |
| replyTime  | 最后回复时间 |
| submitTime | 发帖时间     |
| viewCount  | 浏览次数     |
| replyCount | 回复数       |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 主题帖编号
        "type": 0, // 主题类型，0-普通主题，1-公告
        "title": "a", // 标题
        "shortContent": "a a [图片] eeee", // 短内容，纯文字
        "images": [ // 图片列表（最多三张）
            "http://example.com/1.jpg",
            "http://example.com/2.jpg",
            "http://example.com/3.jpg"
        ],
        "submitTime": "2020-08-04 16:00:00", // 发帖时间
        "submitterAvatarPath": "/api/file/avatar/xxx.jpg", // 发帖人头像地址
        "viewCount": 1, // 浏览次数
        "replyCount": 1, // 回复数
        "boardId": 1, // 板块编号
        "boardName": "a", // 板块名称
        "categoryId": 1, // 分区编号
        "categoryName": "a", // 分区名称
        "lastReplyTime": "2020-08-04 16:00:00", // 最后回复时间
        "lastReplierUserId": 1, // 最后回复者用户编号
        "lastReplierNickname": "a", // 最后回复者昵称
        "pinned": false, // 是否置顶
        "featured": false // 是否精华
    }
]
```

### 【管理】查看所有主题帖列表

`GET /api/topic/topic-list?boardId=&username=&type=&keyword=&from=&to=&sort=&page=&count=`

#### 权限

版主、分区版主、超级版主、管理员

#### 参数

| 字段名   | 字段类型 | 必填 | 含义                              | 样例                |
| -------- | -------- | ---- | --------------------------------- | ------------------- |
| boardId  | int      | 否   | 按板块编号筛选                    | 1                   |
| username | string   | 否   | 按发帖者用户名精确筛选            | a                   |
| type     | string   | 否   | 按主题帖类型筛选，默认为 `normal` | normal              |
| keyword  | string   | 否   | 按标题关键字筛选                  | a                   |
| from     | datetime | 否   | 按发帖开始时间筛选                | 2020-01-01 00:00:00 |
| to       | datetime | 否   | 按发帖结束时间筛选                | 2020-01-05 23:59:59 |
| sort     | string   | 否   | 排序方式，默认为 `submitTime`     | submitTime          |
| page     | int      | 是   | 页码                              | 1                   |
| count    | int      | 是   | 一次获取的个数，上限20            | 20                  |

主题帖类型：

| type         | 含义              |
| ------------ | ----------------- |
| normal       | 普通主题+精华主题 |
| featured     | 精华主题          |
| pinned       | 置顶主题          |
| announcement | 公告主题          |
| all          | 全部主题          |

排序方式：

| sort       | 含义     |
| ---------- | -------- |
| submitTime | 发帖时间 |
| viewCount  | 浏览次数 |
| replyCount | 回复数   |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 主题帖编号
        "type": 0, // 主题类型，0-普通主题，1-公告
        "title": "a", // 标题
        "content": "a <b>a</b> <img src='a.jpg' /> eeee", // 内容
        "shortContent": "a a [图片] eeee", // 短内容，纯文字
        "submitTime": "2020-08-04 16:00:00", // 发帖时间
        "submitterUserId": 1, // 发帖人用户编号
        "submitterUsername": "a", // 发帖人用户名
        "submitterNickname": "a", // 发帖人昵称
        "submitterIp": "9.9.9.9", // 发帖人IP
        "boardId": 1, // 板块编号
        "boardName": "a", // 板块名称
        "categoryId": 1, // 分区编号
        "categoryName": "a", // 分区名称
        "viewCount": 1, // 浏览次数
        "replyCount": 1, // 回复数
        "pinned": false, // 是否置顶
        "featured": false, // 是否精华
        "editTime": "2020-08-04 16:00:00", // 最后编辑时间，无编辑则为null
        "editorUserId": "a", // 最后编辑者用户编号，无编辑则为null
        "editorUsername": "a", // 最后编辑者用户名，无编辑则为null
        "editorNickname": "a", // 最后编辑者昵称，无编辑则为null
        "editorIp": "12.34.56.78" // 最后编辑者IP，无编辑则为null
    }
]
```

### 查看指定主题帖详细信息

`GET /api/topic/topic-detail?topicId=`

#### 权限

有所属板块访问权限的所有用户

#### 参数

| 字段名  | 字段类型 | 必填 | 含义       | 样例 |
| ------- | -------- | ---- | ---------- | ---- |
| topicId | int      | 是   | 主题帖编号 | 1    |

#### 操作成功时返回对象

```json5
{
    "id": 1, // 主题帖编号
    "type": 0, // 主题类型，0-普通主题，1-公告
    "title": "a", // 标题
    "content": "a <b>a</b> <img src='a.jpg' /> eeee", // 内容
    "submitTime": "2020-08-04 16:00:00", // 发帖时间
    "submitterUserId": 1, // 发帖人用户编号
    "submitterNickname": "a", // 发帖人昵称
    "submitterSignature": "a", // 发帖人个人签名
    "submitterAvatarPath": "/api/file/avatar/xxx.jpg", // 发帖人头像地址
    "boardId": 1, // 板块编号
    "boardName": "a", // 板块名称
    "categoryId": 1, // 分区编号
    "categoryName": "a", // 分区名称
    "viewCount": 1, // 浏览次数
    "replyCount": 1, // 回复数
    "lastReplyTime": "2020-08-04 16:00:00", // 最后回复时间
    "lastReplierUserId": 1, // 最后回复者用户编号
    "lastReplierNickname": "a", // 最后回复者昵称
    "pinned": false, // 是否置顶
    "featured": false, // 是否精华
    "editTime": "2020-08-04 20:00:00", // 最后编辑时间，无编辑则为null
    "editorUserId": 2, // 最后编辑者用户编号，无编辑则为null
    "editorNickname": "a", // 最后编辑者昵称，无编辑则为null
    "attachments": [ // 附件
        {
            "id": 1, // 附件编号
            "filename": "a.exe", // 文件名
            "fileSize": 114514, // 文件大小（字节）
            "description": "asdas", // 描述
            "downloadUrl": "/api/file/attachment/1XaTql.exe", // 下载地址
            "downloadCount": 1, // 下载次数
            "uploadTime": "2020-08-04 23:34:00" // 上传时间
        }
    ]
}
```

### 发布主题帖

`POST /api/topic/add-topic`

#### 权限

- 普通主题：有所属板块发表主题帖权限的所有用户
- 公告：有所属板块发表主题帖权限的版主、分区版主、超级版主、管理员

#### 参数

```json5
{
    "type": 0, // 主题类型，0-普通主题，1-公告
    "title": "a", // 标题，必填，最大120字符
    "content": "a", // 内容，必填，最大150000字符
    "boardId": 1, // 板块编号，必填
    "attachments": [ // 附件列表，最多10个附件，通过上传附件获取编号
        {
            "id": 1, // 附件编号
            "description": "a" // 附件描述
        },
        {
            "id": 2,
            "description": "b"
        }
    ],
    "verifyCode": "abcd", // 图形验证码，必填，4位
    "verifyCodeRandom": "sOdQceeE" // 获取验证码时的随机字符串
}
```

#### 操作成功时返回对象

```json5
{
    "id": 1 // 主题帖编号，发帖成功后根据编号进入对应主题帖
}
```

### 编辑主题帖

`POST /api/topic/update-topic`

#### 权限

- 普通主题：发帖者、版主、分区版主、超级版主、管理员
- 公告：发帖者、管理员

#### 参数

```json5
{
    "title": "a", // 标题，必填，最大120字符
    "content": "a", // 内容，必填，最大150000字符
    "attachments": [ // 附件列表，最多10个附件，通过上传附件获取编号
        {
            "id": 1, // 附件编号
            "description": "a" // 附件描述
        },
        {
            "id": 2,
            "description": "b"
        }
    ]
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】主题帖管理（单个+批量）

`POST /api/topic/manage-topic`

#### 权限

- 普通主题：版主、分区版主、超级版主、管理员
- 公告：管理员

#### 参数

```json5
{
    "idList": [ // 主题帖编号列表
        1,
        2,
        3
    ],
    "action": "pin", // 操作
    "reason": "a" // 操作原因，操作不是删除时必填，最大200字符
}
```

操作：

| action    | 含义     |
| --------- | -------- |
| delete    | 删除     |
| pin       | 置顶     |
| unpin     | 取消置顶 |
| feature   | 精华     |
| unfeature | 取消精华 |

#### 操作成功时返回对象

```json5
null
```

### 发帖者删除主题帖

`POST /api/topic/delete-topic`

#### 权限

发帖者

#### 参数

```json5
{
    "id": 1 // 主题帖编号
}
```

#### 操作成功时返回对象

```json5
null
```

### 查看指定主题帖的操作记录

`GET /api/topic/topic-operation-log?topicId=`

#### 权限

所属板块的版主、超级版主、管理员

#### 参数

| 字段名  | 字段类型 | 必填 | 含义       | 样例 |
| ------- | -------- | ---- | ---------- | ---- |
| topicId | int      | 是   | 主题帖编号 | 1    |

#### 操作成功时返回对象

```json5
[
    {
        "operatorUserId": 1, // 操作者用户编号
        "operatorNickname": "a", // 操作者昵称
        "operatorIp": "5.5.5.5", // 操作者IP
        "reason": "a", // 操作原因
        "operateTime": "2020-08-05 23:30:00" // 操作时间
    }
]
```

## 回复帖

### 查看指定主题帖的回复帖

`GET /api/reply/topic-reply-list?topicId=&page=&count=`

#### 权限

无

#### 参数

| 字段名  | 字段类型 | 必填 | 含义                   | 样例 |
| ------- | -------- | ---- | ---------------------- | ---- |
| topicId | int      | 是   | 主题帖编号             | 1    |
| page    | int      | 是   | 页码                   | 1    |
| count   | int      | 是   | 一次获取的个数，上限20 | 20   |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 回复帖编号
        "content": "a <b>a</b> <img src='a.jpg' /> eeee", // 内容
        "replyTime": "2020-08-04 16:00:00", // 回复时间
        "replierUserId": 1, // 回复者用户编号
        "replierNickname": "a", // 回复者昵称
        "replierSignature": "a", // 回复者个人签名
        "replierAvatarPath": "/api/file/avatar/xxx.jpg", // 回复者头像地址
        "editTime": "2020-08-04 16:00:00", // 最后编辑时间
        "editorUserId": 1, // 编辑者用户编号
        "editorNickname": "a" // 编辑者昵称
    }
]
```

### 查看指定用户的回复帖列表

`GET /api/reply/user-reply-list?userId=&page=&count=`

#### 权限

无

#### 参数

| 字段名 | 字段类型 | 必填 | 含义                   | 样例 |
| ------ | -------- | ---- | ---------------------- | ---- |
| userId | int      | 是   | 用户编号               | 1    |
| page   | int      | 是   | 页码                   | 1    |
| count  | int      | 是   | 一次获取的个数，上限20 | 20   |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 主题帖编号
        "shortContent": "a a [图片] eeee", // 短内容，纯文字
        "topicId": 1, // 所属主题帖编号
        "topicTitle": "a", // 所属主题帖标题
        "submitterUserId": 1, // 发帖人用户编号
        "submitterNickname": "a", // 发帖人昵称
        "submitterAvatarPath": "/api/file/avatar/xxx.jpg", // 发帖人头像地址
        "replierUserId": 1, // 回复者用户编号
        "replierNickname": "a", // 回复者昵称
        "replyTime": "2020-08-04 16:00:00" // 发帖时间
    }
]
```

### 【管理】查看所有回复帖列表

`GET /api/reply/reply-list?boardId=&username=&keyword=&from=&to=&page=&count=`

#### 权限

版主、分区版主、超级版主、管理员

#### 参数

| 字段名   | 字段类型 | 必填 | 含义                   | 样例                |
| -------- | -------- | ---- | ---------------------- | ------------------- |
| boardId  | int      | 否   | 按所属板块编号筛选     | 1                   |
| username | string   | 否   | 按发帖者用户名筛选     | a                   |
| keyword  | string   | 否   | 按标题关键字筛选       | a                   |
| from     | datetime | 否   | 按发帖开始时间筛选     | 2020-01-01 00:00:00 |
| to       | datetime | 否   | 按发帖结束时间筛选     | 2020-01-05 23:59:59 |
| page     | int      | 是   | 页码                   | 1                   |
| count    | int      | 是   | 一次获取的个数，上限20 | 20                  |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 主题帖编号
        "content": "a <b>a</b> <img src='a.jpg' /> eeee", // 内容
        "shortContent": "a a [图片] eeee", // 短内容，纯文字
        "topicId": 1, // 所属主题帖编号
        "topicTitle": "a", // 所属主题帖标题
        "replyTime": "2020-08-04 16:00:00", // 回复时间
        "replierUserId": 1, // 回复者用户编号
        "replierUsername": "a", // 回复者用户名
        "replierNickname": "a", // 回复者昵称
        "replierIp": "9.9.9.9", // 回复者IP
        "editTime": "2020-08-04 16:00:00", // 最后编辑时间
        "editorUserId": "a", // 最后编辑者用户编号
        "editorUsername": "a", // 最后编辑者用户名
        "editorNickname": "a", // 最后编辑者昵称
        "editorIp": "12.34.56.78" // 最后编辑者IP
    }
]
```

### 发表回复帖

`POST /api/reply/add-reply`

#### 权限

有所属板块发表回复帖权限的所有用户

#### 参数

```json5
{
    "topicId": 1, // 主题帖编号
    "content": "a", // 内容，必填，最大150000字符
    "verifyCode": "abcd", // 图形验证码，必填，4位
    "verifyCodeRandom": "sOdQceeE" // 获取验证码时的随机字符串
}
```

#### 操作成功时返回对象

```json5
{
    "id": 1 // 主题帖编号，发帖成功后根据编号进入对应主题帖
}
```

### 编辑回复帖

`POST /api/reply/update-reply`

#### 权限

发帖者、版主、分区版主、超级版主、管理员

#### 参数

```json5
{
    "replyId": 1, // 回复帖编号
    "content": "a" // 内容，必填，最大150000字符
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】批量删除回复帖

`POST /api/reply/batch-delete-reply`

#### 权限

版主、分区版主、超级版主、管理员

#### 参数

```json5
{
    "idList": [ // 回复帖编号列表
        1,
        2,
        3
    ]
}
```

#### 操作成功时返回对象

```json5
null
```

### 发帖者删除回复帖

`POST /api/reply/delete-reply`

#### 权限

发帖者

#### 参数

```json5
{
    "id": 1 // 回复帖编号
}
```

#### 操作成功时返回对象

```json5
null
```

## 附件、图片

### 【管理】查看附件列表

`GET /api/attachment/attachment-list?boardId=&username=&filename=&from=&to=&page=&count=`

#### 权限

管理员

#### 参数

| 字段名   | 字段类型 | 必填 | 含义                   | 样例                |
| -------- | -------- | ---- | ---------------------- | ------------------- |
| boardId  | int      | 否   | 按所属板块编号筛选     | 1                   |
| username | string   | 否   | 按上传者用户名精确筛选 | a                   |
| filename | string   | 否   | 按文件名筛选           | a                   |
| from     | datetime | 否   | 按上传开始时间筛选     | 2020-01-01 00:00:00 |
| to       | datetime | 否   | 按上传结束时间筛选     | 2020-01-05 23:59:59 |
| page     | int      | 是   | 页码                   | 1                   |
| count    | int      | 是   | 一次获取的个数，上限20 | 20                  |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 附件编号
        "topicId": 1, // 所属主题编号
        "topicTitle": "a", // 所属主题标题
        "boardId": 1, // 所属板块编号
        "boardName": "a", // 所属板块名称
        "filename": "a", // 文件名
        "fileSize": 1, // 文件大小（字节）
        "description": "a", // 描述
        "downloadUrl": "/api/file/attachment/1XaTql.exe", // 下载地址
        "downloadCount": 1, // 下载次数
        "uploadTime": "2020-08-04 23:34:00", // 上传时间
        "uploaderUserId": 1, // 上传者用户编号
        "uploaderUsername": "a", // 上传者用户名
        "uploaderIp": "2.2.2.2" // 上传者IP
    }
]
```

### 上传图片

`POST /api/attachment/upload-image`

#### 权限

有发主题帖/回复主题帖权限的所有用户

#### 参数

图片文件，支持jpg/png/gif，最大2MB

#### 操作成功时返回对象

```json5
{
    "imageUrl": "/api/file/image/xxx.jpg" // 图片url
}
```

### 上传附件

`POST /api/attachment/upload-attachment`

#### 权限

有上传附件权限的所有用户

#### 参数

附件文件，最大2MB

#### 操作成功时返回对象

```json5
{
    "id": 1, // 附件编号
    "downloadUrl": "/api/file/attachment/a.exe" // 下载链接
}
```

### 【管理】删除附件

`POST /api/attachment/delete-attachment`

#### 权限

管理员

#### 参数

```json5
{
    "idList": [ // 附件编号列表
        1,
        2,
        3
    ]
}
```

#### 操作成功时返回对象

```json5
null
```

## 板块

### 【管理】查看所有板块

`GET /api/board/board-list?page=&count=`

#### 权限

版主、分区版主、超级版主、管理员

#### 参数

| 字段名 | 字段类型 | 必填 | 含义                   | 样例 |
| ------ | -------- | ---- | ---------------------- | ---- |
| page   | int      | 是   | 页码                   | 1    |
| count  | int      | 是   | 一次获取的个数，上限20 | 20   |

#### 操作成功时返回对象

版主、分区版主只返回管理的板块  
超级版主、管理员返回所有板块

```json5
[
    {
        "id": 1, // 板块编号
        "name": "a", // 板块名称
        "categoryId": 1, // 所属分区编号
        "categoryName": "a", // 所属分区名称
        "description": "a", // 描述
        "visible": true, // 是否可见
        "order": 1, // 显示顺序
        "boardPermission": { // 普通会员在本版块的权限
            "banVisit": false, // 禁止访问
            "banCreateTopic": false, // 禁止创建主题帖
            "banReply": false, // 禁止回复
            "banUploadAttachment": false, // 禁止上传附件
            "banDownloadAttachment": false // 禁止下载附件
        },
        "boardAdmin": [ // 版主（不包括分区版主）
            {
                "id": 1, // 用户编号
                "username": "aa" // 用户名
            }
        ],
        "createTime": "2020-08-08 01:11:11", // 创建时间
        "updateTime": "2020-08-08 01:11:11" // 更新时间
    }
]
```

### 【管理】查看板块名称列表

`GET /api/board/board-name-list`

#### 权限

版主、分区版主、超级版主、管理员

#### 参数

无

#### 操作成功时返回对象

版主、分区版主只返回管理的板块  
超级版主、管理员返回所有板块

```json5
[
    {
        "id": 1, // 分区编号
        "name": "a", // 分区名称
        "boardList": [ // 板块列表
            {
                "id": 1, // 板块编号
                "name": "a" // 板块名称
            },
            {
                "id": 2,
                "name": "b"
            }
        ]
    },
    {
        "id": 2,
        "name": "aa",
        "boardList": [
            {
                "id": 3,
                "name": "c"
            }
        ]
    }
]
```

### 【管理】创建板块

`POST /api/board/add-board`

#### 权限

管理员

#### 参数

```json5
{
    "name": "a", // 板块名称，必填，最大30字符
    "categoryId": 1, // 所属分区编号，必填
    "description": "a", // 描述，必填，最大200字符
    "visible": true, // 是否可见，必填
    "order": 1, // 顺序，必填，1-100
    "boardPermission": { // 普通会员在本版块的权限
        "banVisit": false, // 禁止访问
        "banCreateTopic": false, // 禁止创建主题帖
        "banReply": false, // 禁止回复
        "banUploadAttachment": false, // 禁止上传附件
        "banDownloadAttachment": false // 禁止下载附件
    }
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】编辑板块

`POST /api/board/update-board`

#### 权限

管理员

#### 参数

```json5
{
    "id": 1, // 板块编号
    "name": "a", // 板块名称，必填，最大30字符
    "description": "a", // 描述，必填，最大200字符
    "visible": true, // 是否可见，必填
    "order": 1, // 顺序，必填，1-100
    "boardPermission": { // 普通会员在本版块的权限
        "banVisit": false, // 禁止访问
        "banCreateTopic": false, // 禁止创建主题帖
        "banReply": false, // 禁止回复
        "banUploadAttachment": false, // 禁止上传附件
        "banDownloadAttachment": false // 禁止下载附件
    }
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】删除板块

`POST /api/board/delete-board`

#### 权限

管理员

#### 参数

```json5
{
    "id": 1 // 板块编号
}
```

#### 操作成功时返回对象

```json5
null
```

## 分区

### 【管理】查看所有分区

`GET /api/category/category-list?page=&count=`

#### 权限

分区版主、超级版主、管理员

#### 参数

| 字段名 | 字段类型 | 必填 | 含义                   | 样例 |
| ------ | -------- | ---- | ---------------------- | ---- |
| page   | int      | 是   | 页码                   | 1    |
| count  | int      | 是   | 一次获取的个数，上限20 | 20   |

#### 操作成功时返回对象

分区版主只返回管理的分区  
超级版主、管理员返回所有分区

```json5
[
    {
        "id": 1, // 板块编号
        "name": "a", // 板块名称
        "description": "a", // 描述
        "visible": true, // 是否可见
        "order": 1, // 显示顺序
        "categoryAdmin": [ // 分区版主
            {
                "id": 1, // 用户编号
                "username": "aa" // 用户名
            }
        ],
        "createTime": "2020-08-08 01:11:11", // 创建时间
        "updateTime": "2020-08-08 01:11:11" // 更新时间
    }
]
```

### 【管理】查看分区名称列表

`GET /api/category/category-name-list`

#### 权限

分区版主、超级版主、管理员

#### 参数

无

#### 操作成功时返回对象

分区版主只返回管理的分区  
超级版主、管理员返回所有分区

```json5
[
    {
        "id": 1, // 分区编号
        "name": "a" // 分区名称
    },
    {
        "id": 2,
        "name": "b"
    }
]
```

### 【管理】创建分区

`POST /api/category/add-category`

#### 权限

管理员

#### 参数

```json5
{
    "name": "a", // 分区名称，必填，最大30字符
    "description": "a", // 描述，必填，最大200字符
    "visible": true, // 是否可见，必填
    "order": 1 // 顺序，必填，1-100
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】编辑分区

`POST /api/category/update-category`

#### 权限

管理员

#### 参数

```json5
{
    "id": 1, // 分区编号
    "name": "a", // 分区名称，必填，最大30字符
    "description": "a", // 描述，必填，最大200字符
    "visible": true, // 是否可见，必填
    "order": 1 // 顺序，必填，1-100
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】删除分区

`POST /api/category/delete-category`

#### 权限

管理员

#### 参数

```json5
{
    "id": 1 // 分区编号
}
```

#### 操作成功时返回对象

```json5
null
```

## 友情链接

### 查看所有友情链接

`GET /api/link/link-list`

#### 权限

无

#### 参数

无

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 友情链接编号
        "name": "a", // 名称
        "description": "a", // 描述
        "url": "http://example.com", // 网址
        "iconUrl": "http://example.com/favicon.ico", // 图标地址，无图标时为null
        "order": 1, // 顺序，小的在前面
        "createTime": "2020-08-08 01:11:11", // 创建时间【管理员】
        "updateTime": "2020-08-08 01:11:11" // 更新时间【管理员】
    }
]
```

### 【管理】添加友情链接

`POST /api/link/add-link`

#### 权限

管理员

#### 参数

```json5
{
    "name": "a", // 名称，必填，最大30字符
    "description": "a", // 描述，最大200字符
    "url": "http://example.com", // 网址，必填，最大255字符
    "iconUrl": "http://example.com/favicon.ico", // 图标地址，最大255字符，无图标时空字符串即可
    "order": 1 // 顺序，必填，1-100
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】编辑友情链接

`POST /api/link/update-link`

#### 权限

管理员

#### 参数

```json5
{
    "id": 1, // 友情链接编号
    "name": "a", // 名称，必填，最大30字符
    "description": "a", // 描述，最大200字符
    "url": "http://example.com", // 网址，必填，最大255字符
    "iconUrl": "http://example.com/favicon.ico", // 图标地址，最大255字符，无图标时空字符串即可
    "order": 1 // 顺序，必填，1-100
}
```

#### 操作成功时返回对象

```json5
null
```

### 【管理】删除友情链接

`POST /api/link/delete-link`

#### 权限

管理员

#### 参数

```json5
{
    "id": 1 // 友情链接编号
}
```

#### 操作成功时返回对象

```json5
null
```

## 操作记录

### 查看全站操作记录列表

`GET /api/log/operation-log?username=&page=&count=`

#### 权限

管理员

#### 参数

| 字段名   | 字段类型 | 必填 | 含义                   | 样例 |
| -------- | -------- | ---- | ---------------------- | ---- |
| username | string   | 否   | 按操作者用户名精确筛选 | a    |
| page     | int      | 是   | 页码                   | 1    |
| count    | int      | 是   | 一次获取的个数，上限20 | 20   |

#### 操作成功时返回对象

```json5
[
    {
        "id": 1, // 操作编号
        "operatorUserId": 1, // 操作者用户编号
        "operatorUsername": "a", // 操作者用户名
        "operatorIp": "2.3.4.5", // 操作者IP
        "type": "删除用户", // 操作类型
        "detail": "删除了编号为 1 的用户，用户名为 admin", // 详细信息
        "operateTime": "2020-08-08 03:00:00" // 操作时间
    }
]
```

## 验证码

### 获取验证码

`GET /api/verifycode/refresh-code`

#### 权限

无

#### 参数

无

#### 操作成功时返回对象

```json5
{
    "url": "/api/verifycode/get-image?random=xxxxx", // 图片url
    "verifyCodeRandom": "adEADOAaqd" // 随机字符串，用于区分不同页面的验证码，提交时带上
}
```

## 其他

### 论坛主页获取分区和板块信息

`GET /api/forum/home-board-list`

#### 权限

无

#### 参数

无

#### 操作成功时返回对象

根据登录的用户类型和权限动态返回分区和板块

```json5
[
    {
        "id": 1, // 分区编号
        "name": "a", // 分区名称
        "description": "a", // 分区描述
        "boardList": [ // 板块列表
            {
                "id": 1, // 板块编号
                "name": "a", // 板块名称
                "description": "a" // 板块描述
            },
            {
                "id": 2,
                "name": "b",
                "description": "b"
            }
        ]
    },
    {
        "id": 2,
        "name": "aa",
        "description": "aa",
        "boardList": [
            {
                "id": 3,
                "name": "c",
                "description": "c"
            }
        ]
    }
]
```
