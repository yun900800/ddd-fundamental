#### Linux基础知识积累
1. 标准出入和参数的区别
    - 1.1 标准输入就是编程语言中诸如scanf或者readline这种命令；而参数是指程序的main函数传入的args字符数组。
    - 1.2 管道符和重定向符是将数据作为程序的标准输入，而$(cmd)是读取cmd命令输出的数据作为参数。
    - 1.3 如果命令能够让终端阻塞，说明该命令接收标准输入，反之就是不接受，比如你只运行cat命令不加任何参数，终端就会阻塞，等待你输入字符串并回显相同的字符串。
    - 1.4 接收标准输入的命令需要使用ctrl+D来退出接收标准输入的交互状态
2. 后台运行程序
    - 2.1 
      ```shell
      $ python manager.py runserver 0.0.0.0
        Listening on 0.0.0.0:8080...
      ```
      现在你可以通过服务器的 IP 地址测试 Django 服务，但是终端此时就阻塞了，你输入什么都不响应，除非输入 Ctrl-C 或者 Ctrl-/ 终止 python 进程。
      可以在命令之后加一个&符号，这样命令行不会阻塞，可以响应你后续输入的命令，但是如果你退出服务器的登录，就不能访问该网页了。
      如果你想在退出服务器之后仍然能够访问 web 服务，应该这样写命令 (cmd &)：
      ```shell
      $ (python manager.py runserver 0.0.0.0 &)
      Listening on 0.0.0.0:8080...

      $ logout
      ```
      底层原理是这样的：
      每一个命令行终端都是一个 shell 进程，你在这个终端里执行的程序实际上都是这个 shell 进程分出来的子进程。正常情况下，shell 进程会阻塞，等待子进程退出才重新接收你输入的新的命令。加上&号，只是让 shell 进程不再阻塞，可以继续响应你的新命令。但是无论如何，你如果关掉了这个 shell 命令行端口，依附于它的所有子进程都会退出。
      而(cmd &)这样运行命令，则是将cmd命令挂到一个systemd系统守护进程名下，认systemd做爸爸，这样当你退出当前终端时，对于刚才的cmd命令就完全没有影响了。
      类似的，还有一种后台运行常用的做法是这样：
      ```shell
      $ nohup some_cmd &
      ```
      nohub命令也是类似的原理，不过通过我的测试，还是(cmd &)这种形式更加稳定。
3. 单引号和双引号的区别
    - 3.1 不同的 shell 行为会有细微区别，但有一点是确定的，对于$，(，)这几个符号，单引号包围的字符串不会做任何转义，双引号包围的字符串会转义。
      开启命令回显 set -x
      比较下面三个命令的输入区别
      echo $(who)
      echo '$(who)'
      echo "$(who)"
      也就是说，如果 $ 读取出的参数字符串包含空格，应该用双引号括起来，否则就会出错。
4. sudo 找不到命令
   有时候我们普通用户可以用的命令，用 sudo 加权限之后却报错 command not found：
      ```shell
      $ connect.sh
      network-manager: Permission denied

      $ sudo connect.sh
      sudo: command not found
      ```
      原因在于，connect.sh 这个脚本仅存在于该用户的环境变量中：
      ```shell
      $ where connect.sh 
      /home/fdl/bin/connect.sh
      ```
      当使用 sudo 时，系统会使用 /etc/sudoers 这个文件中规定的该用户的权限和环境变量，而这个脚本在 /etc/sudoers 环境变量目录中当然是找不到的。

      解决方法是使用脚本文件的路径，而不是仅仅通过脚本名称：
      ```shell
      $ sudo /home/fdl/bin/connect.sh
      ```
      set -x 开启调试 set +x 关闭调试
5. 输入相似文件名太麻烦
   - 5.1 用花括号括起来的字符串用逗号连接，可以自动扩展，非常有用，直接看例子：
      ```shell
      $ echo {one,two,three}file
      onefile twofile threefile

      $ echo {one,two,three}{1,2,3}
      one1 one2 one3 two1 two2 two3 three1 three2 three3
      ```
     你看，花括号中的每个字符都可以和之后（或之前）的字符串进行组合拼接，注意花括号和其中的逗号不可以用空格分隔，否则会被认为是普通的字符串对待。
     这个技巧有什么实际用处呢？最简单有用的就是给 cp, mv, rm 等命令扩展参数：
     ```shell
      $ cp /very/long/path/file{,.bak}
      # 给 file 复制一个叫做 file.bak 的副本

      $ rm file{1,3,5}.txt
      # 删除 file1.txt file3.txt file5.txt

      $ mv *.{c,cpp} src/
      # 将所有 .c 和 .cpp 为后缀的文件移入 src 文件夹
     ```
6. 输入路径名称太麻烦
   - 6.1 用 cd - 返回刚才呆的目录，直接看例子吧：
     ```shell
      $ pwd
      /very/long/path
      $ cd # 回到家目录瞅瞅
      $ pwd
      /home/labuladong
      $ cd - # 再返回刚才那个目录
      $ pwd
      /very/long/path
     ```
   - 6.2 特殊命令 !$ 会替换成上一次命令最后的路径，直接看例子：
     ```shell
      # 没有加可执行权限
      $ /usr/bin/script.sh
      zsh: permission denied: /usr/bin/script.sh

      $ chmod +x !$
      chmod +x /usr/bin/script.sh
     ```
   - 6.3 特殊命令 !* 会替换成上一次命令输入的所有文件路径，直接看例子：
     ```shell
      # 创建了三个脚本文件
      $ file script1.sh script2.sh script3.sh

      # 给它们全部加上可执行权限
      $ chmod +x !*
      chmod +x script1.sh script2.sh script3.sh
     ```
   - 6.4 可以在环境变量 CDPATH 中加入你常用的工作目录，当 cd 命令在当前目录中找不到你指定的文件/目录时，会自动到 CDPATH 中的目录中寻找。
     ```shell
      $ export CDPATH='~:/var/log'
      # cd 命令将会在 ～ 目录和 /var/log 目录扩展搜索

      $ pwd
      /home/labuladong/musics
      $ cd mysql
      cd /var/log/mysql
      $ pwd
      /var/log/mysql
      $ cd my_pictures
      cd /home/labuladong/my_pictures
     ```
     这个技巧是十分好用的，这样就免了经常写完整的路径名称，节约不少时间。
     需要注意的是，以上操作是 bash 支持的，其他主流 shell 解释器当然都支持扩展 cd 命令的搜索目录，但可能不是修改 CDPATH 这个变量，具体的设置方法可以自行搜索。