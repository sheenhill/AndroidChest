# AndroidChest
用于存放日常Android学习demo和实验demo的仓库

# 架构
当前以组件化的模式开发，将若干个小型APP以组件的形式整合到APP中。

为了单个小型APP的调试，所以使用多组件集成模式，通过gradle可以单组件单独编译成apk，也可以把每个组件当做一个模块集成到主项目。（通过Gradle实现，博客敬请期待...）

2020年用AndroidX+JetPack+kotlin重构绝大部分代码。

**注：**每个组件（除主工程、common以外）都用`/main/[模块名+fragment]`为入口，这样方便主工程使用单activity多fragment的形式去加载。