require_relative '../append_field_update_generator'


class AppendUuidFieldUpdateGenerator < AppendFieldUpdateGenerator

  def generate_required_code
    "            updates.add(Updates.set(path().path(#{field_conf.store_name_const_name}), #{to_bson_value_code("#{field_conf.getter_name}()")}));\n"
  end

  def generate_optional_append_update_code
    "                updates.add(Updates.set(path().path(#{field_conf.store_name_const_name}), #{to_bson_value_code(temp_field_name)}));\n"
  end

  private
  def to_bson_value_code(variable_name)
    if field_conf.has_modifier?('legacy')
      "new BsonBinary(#{variable_name}, UuidRepresentation.JAVA_LEGACY)"
    else
      "new BsonBinary(#{variable_name})"
    end
  end

end
