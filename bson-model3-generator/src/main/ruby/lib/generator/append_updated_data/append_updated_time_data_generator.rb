require_relative '../append_updated_field_data_generator'


class AppendUpdatedTimeDataGenerator < AppendUpdatedFieldDataGenerator

  def generate_append_code
    code = ''
    if field_conf.required?
      code << "            data.put(#{field_conf.display_name_const_name}, #{field_conf.getter_name}().format(BsonModelConstants.TIME_FORMATTER));\n"
    else
      code << "            var #{temp_field_name} = #{field_conf.getter_name}();\n"
      code << "            if (#{temp_field_name} != null) {\n"
      code << "                data.put(#{field_conf.display_name_const_name}, #{temp_field_name}.format(BsonModelConstants.TIME_FORMATTER));\n"
      code << "            }\n"
    end
  end

end
