import 'package:json_annotation/json_annotation.dart';

part 'UserInfo.g.dart';

@JsonSerializable(nullable: false)
class UserInfo{
  int id;
  @JsonKey(name: "avatar_url")
  String avatarUrl;

  UserInfo(this.id,this.avatarUrl);

  factory UserInfo.fromJson(Map<String, dynamic> json) => _$UserInfoFromJson(json);
}