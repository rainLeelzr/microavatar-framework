@echo off 
echo 把协议定义好存放在ProtoFile文件夹中
.\Builder\protogen -i:.\ProtoFile\BattleMsg.proto -o:.\C#Script\Battle\BattleMsg.cs
.\Builder\protogen -i:.\ProtoFile\HallMsg.proto -o:.\C#Script\Hall\HallMsg.cs
.\Builder\protogen -i:.\ProtoFile\LoginMsg.proto -o:.\C#Script\Login\LoginMsg.cs
echo 文件已生成在C#Script文件夹中
pause