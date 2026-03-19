require_relative '../to_display_field_data_generator'


class ToDisplayStdListDataGenerator < ToDisplayFieldDataGenerator

  def generate_required_display_data_code
    "        _displayData.put(#{field_conf.display_name_const_name}, #{list_map_code("#{field_conf.getter_name}()")});\n"
  end

  def generate_optional_variable_display_data_code
    "            _displayData.put(#{field_conf.display_name_const_name}, #{list_map_code(temp_field_name)});\n"
  end

  private
  def list_map_code(variable_name)
    case field_conf.value
    when 'object'
      "CommonsUtil.mapToDisplayDataList(#{variable_name})"
    when 'date'
      "CommonsUtil.mapToDisplayDataList(#{variable_name}, LocalDate::toString)"
    when 'time'
      "CommonsUtil.mapToDisplayDataList(#{variable_name}, BsonModelConstants.TIME_FORMATTER::format)"
    when 'datetime'
      "CommonsUtil.mapToDisplayDataList(#{variable_name}, BsonModelConstants.DATETIME_FORMATTER::format)"
    when 'object-id'
      "CommonsUtil.mapToDisplayDataList(#{variable_name}, ObjectId::toHexString)"
    when 'uuid'
      "CommonsUtil.mapToDisplayDataList(#{variable_name}, UUID::toString)"
    else
      "new ArrayList<>(#{variable_name})"
    end
  end

end
