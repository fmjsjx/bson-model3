require_relative '../to_display_field_data_generator'


class ToDisplayDateTimeDataGenerator < ToDisplayFieldDataGenerator

  def generate_required_display_data_code
    "        _displayData.put(#{field_conf.display_name_const_name}, #{field_conf.getter_name}().format(BsonModelConstants.DATETIME_FORMATTER));\n"
  end

  def generate_optional_variable_display_data_code
    "            _displayData.put(#{field_conf.display_name_const_name}, #{temp_field_name}.format(BsonModelConstants.DATETIME_FORMATTER));\n"
  end

end
