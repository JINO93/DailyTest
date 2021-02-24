import 'package:json_annotation/json_annotation.dart';

import 'UserInfo.dart';

part 'RepoItemInfo.g.dart';

@JsonSerializable(nullable: false)
class RepoItemInfo{
  int id;
  @JsonKey(name: "name")
  String title;
  @JsonKey(name: "description")
  String desc;
  @JsonKey(name: "owner")
  UserInfo owner;

  RepoItemInfo(this.id,this.title,this.desc,this.owner);


  factory RepoItemInfo.fromJson(Map<String, dynamic> json) => _$RepoItemInfoFromJson(json);
}