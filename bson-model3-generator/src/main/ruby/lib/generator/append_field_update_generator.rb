class AppendFieldUpdateGenerator

  class << self

    def from(config, model_conf, field_conf)
      case field_conf.type
      when 'int'
        AppendIntFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'long'
        AppendLongFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'double'
        AppendDoubleFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'decimal'
        AppendDecimalFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'boolean'
        AppendBooleanFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'string'
        AppendStringFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'date'
        AppendDateFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'time'
        AppendTimeFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        AppendDatetimeFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        AppendObjectIdFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        AppendUuidFieldUpdateGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        AppendStdListFieldUpdateGenerator.new(config, model_conf, field_conf)
      else
        new(config, model_conf, field_conf)
      end
    end

  end

  attr_reader :config, :model_conf, :field_conf, :temp_field_name

  def initialize(config, model_conf, field_conf)
    @config = config
    @model_conf = model_conf
    @field_conf = field_conf
    @temp_field_name = "_#{field_conf.name}"
  end

  def generate
    code = ''
    code << "        if (changedFields.get(#{@field_conf.field_index_const_name})) {\n"
    if @field_conf.required?
      code << generate_required_code
    else
      code << generate_optional_code
    end
    code << "        }\n"
  end

  def generate_required_code
    "            #{@field_conf.getter_name}().appendUpdates(updates);\n"
  end

  def generate_optional_code
    code = ''
    code << "            var #{@temp_field_name} = #{@field_conf.getter_name}();\n"
    code << "            if (#{@temp_field_name} == null) {\n"
    code << "                updates.add(Updates.unset(path().path(#{@field_conf.store_name_const_name})));\n"
    code << "            } else {\n"
    code << generate_optional_append_update_code
    code << "            }\n"
  end

  def generate_optional_append_update_code
    "                #{@temp_field_name}.appendUpdates(updates);\n"
  end

end


require_relative 'append_field_update/append_int_field_update_generator'
require_relative 'append_field_update/append_long_field_update_generator'
require_relative 'append_field_update/append_double_field_update_generator'
require_relative 'append_field_update/append_decimal_field_update_generator'
require_relative 'append_field_update/append_boolean_field_update_generator'
require_relative 'append_field_update/append_string_field_update_generator'
require_relative 'append_field_update/append_date_field_update_generator'
require_relative 'append_field_update/append_time_field_update_generator'
require_relative 'append_field_update/append_datetime_field_update_generator'
require_relative 'append_field_update/append_object_id_field_update_generator'
require_relative 'append_field_update/append_uuid_field_update_generator'
require_relative 'append_field_update/append_std_list_field_update_generator'
