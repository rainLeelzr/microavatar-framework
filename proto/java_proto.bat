@echo OFF 
echo 把协议定义好存放在ProtoFile文件夹中

echo 正在生成《IM》proto
protoc --java_out=../common-protobuf/src/main/java ./ProtoFile/IM.proto

echo 正在生成《BaseFrame》proto
protoc --java_out=../common-protobuf/src/main/java ./ProtoFile/BaseFrame.proto

echo 文件已生成
pause