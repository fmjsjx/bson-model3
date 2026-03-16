require_relative '../to_display_field_data_generator'


class ToDisplayStdListDataGenerator < ToDisplayFieldDataGenerator

  def generate_required_display_data_code
    "        _displayData.put(#{field_conf.display_name_const_name}, #{field_conf.getter_name}()#{value_map_code});\n"
  end

  def generate_optional_variable_display_data_code
    "            _displayData.put(#{field_conf.display_name_const_name}, #{temp_field_name}#{value_map_code});\n"
  end

  private
  def value_map_code
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
