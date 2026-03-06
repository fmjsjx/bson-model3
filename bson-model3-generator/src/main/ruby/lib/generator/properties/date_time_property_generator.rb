require_relative '../property_generator'
require 'json'


class DateTimePropertyGenerator < PropertyGenerator

  def generate_field_declaration_code
    if required?
      if has_default_value?
        "    private LocalDateTime #{name} = #{default_value_code};\n"
      else
        "    private LocalDateTime #{name} = BsonModelConstants.EPOCH_DATE_TIME;\n"
      end
    else
      "    private @Nullable LocalDateTime #{name};\n"
    end
  end

  def generate_getter_code
    code = ''
    if required?
      code << "    public LocalDateTime #{field_conf.getter_name}() {\n"
    else
      code << "    public @Nullable LocalDateTime #{field_conf.getter_name}() {\n"
    end
    if virtual?
      code << "#{virtual_code}\n"
    else
      code << "        return #{name};\n"
    end
    code << "    }\n"
  end

  def generate_setter_code
    code = ''
    if required?
      code << "    public void set#{field_conf.camel_case_name}(LocalDateTime #{name}) {\n"
      if store_field?
        code << "        if (!#{name}.equals(this.#{name})) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    else
      code << "    public void set#{field_conf.camel_case_name}(@Nullable LocalDateTime #{name}) {\n"
      if store_field?
        code << "        if (!Objects.equals(this.#{name}, #{name})) {\n"
        code << "            this.#{name} = #{name};\n"
        code << "            #{field_changed_code}\n"
        code << "        }\n"
      else
        code << "        this.#{name} = #{name};\n"
      end
      code << "    }\n"
    end
  end

  private
  def default_value_code
    value = field_conf.default
    case value.upcase
    when 'MIN'
      'LocalDateTime.MIN'
    when 'MAX'
      'LocalDateTime.MAX'
    when 'EPOCH'
      'BsonModelConstants.EPOCH_DATE_TIME'
    when 'NOW'
      'LocalDateTime.now()'
    else
      if model_conf.consts.any? { |const_conf| const_conf.type == 'datetime' and const_conf.name == value }
        value
      else
        "LocalDateTime.parse(#{value.to_json})"
      end
    end
  end

end
