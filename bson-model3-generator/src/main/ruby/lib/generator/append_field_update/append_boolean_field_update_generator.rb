require_relative '../append_field_update_generator'


class AppendBooleanFieldUpdateGenerator < AppendFieldUpdateGenerator

  def generate_required_code
    "            updates.add(Updates.set(path().path(#{field_conf.store_name_const_name}), BsonBoolean.valueOf(#{field_conf.getter_name}())));\n"
  end

  def generate_optional_append_update_code
    "                updates.add(Updates.set(path().path(#{field_conf.store_name_const_name}), BsonBoolean.valueOf(#{temp_field_name})));\n"
  end

end
