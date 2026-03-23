require_relative '../append_updated_field_data_generator'


class AppendUpdatedStdListDataGenerator < AppendUpdatedFieldDataGenerator

  def generate_append_code
    code = ''
    if field_conf.required?
      code << "            data.put(#{field_conf.display_name_const_name}, #{field_conf.getter_name}()#{optional_value_map_code});\n"
    else
      code << "            var #{temp_field_name} = #{field_conf.getter_name}();\n"
      code << "            if (#{temp_field_name} != null) {\n"
      code << "                data.put(#{field_conf.display_name_const_name}, #{temp_field_name}#{optional_value_map_code});\n"
      code << "            }\n"
    end
  end

  private
  def optional_value_map_code
    case field_conf.value
    when 'object'
      ".stream().map(#{field_conf.model}::toDisplayData).toList()"
    when 'date'
      ".stream().map(LocalDate::toString).toList()"
    when 'time'
      ".stream().map(BsonModelConstants.TIME_FORMATTER::format).toList()"
    when 'datetime'
      ".stream().map(BsonModelConstants.DATETIME_FORMATTER::format).toList()"
    when 'object-id'
      ".stream().map(ObjectId::toHexString).toList()"
    when 'uuid'
      ".stream().map(UUID::toString).toList()"
    else
      ''
    end
  end

end
