require_relative '../append_field_update_generator'


class AppendDecimalFieldUpdateGenerator < AppendFieldUpdateGenerator

  def generate_required_code
    "            updates.add(Updates.set(path().path(#{field_conf.store_name_const_name}), BsonValueUtil.toBsonDecimal128(#{field_conf.getter_name}())));\n"
  end

  def generate_optional_append_update_code
    "                updates.add(Updates.set(path().path(#{field_conf.store_name_const_name}), BsonValueUtil.toBsonDecimal128(#{temp_field_name})));\n"
  end

end
