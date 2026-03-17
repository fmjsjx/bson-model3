class LoadFieldGenerator

  class << self

    def from(config, model_conf, field_conf)
      case field_conf.type
      when 'int'
        LoadIntGenerator.new(config, model_conf, field_conf)
      when 'long'
        LoadLongGenerator.new(config, model_conf, field_conf)
      when 'double'
        LoadDoubleGenerator.new(config, model_conf, field_conf)
      when 'decimal'
        LoadDecimalGenerator.new(config, model_conf, field_conf)
      when 'boolean'
        LoadBooleanGenerator.new(config, model_conf, field_conf)
      when 'string'
        LoadStringGenerator.new(config, model_conf, field_conf)
      when 'date'
        LoadDateGenerator.new(config, model_conf, field_conf)
      when 'time'
        LoadTimeGenerator.new(config, model_conf, field_conf)
      when 'datetime'
        LoadDateTimeGenerator.new(config, model_conf, field_conf)
      when 'object-id'
        LoadObjectIdGenerator.new(config, model_conf, field_conf)
      when 'uuid'
        LoadUuidGenerator.new(config, model_conf, field_conf)
      when 'std-list'
        LoadStdListGenerator.new(config, model_conf, field_conf)
      when 'map'
        LoadMapGenerator.new(config, model_conf, field_conf)
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
    @temp_field_name = "_#{@field_conf.name}"
  end

  def generate
    if @field_conf.required? and not @field_conf.readonly?
      generate_load_required_field_code
    else
      generate_load_optional_field_code
    end
  end

  def generate_load_required_field_code
    "        BsonUtil.documentValue(src, #{@field_conf.store_name_const_name}).ifPresentOrElse(#{@field_conf.getter_name}()::load, #{@field_conf.getter_name}()::clean);\n"
  end

  def generate_load_optional_field_code
    code = ''
    code << "        BsonUtil.documentValue(src, #{@field_conf.store_name_const_name}).ifPresentOrElse(\n"
    code << "                it -> {\n"
    code << "                    var #{@temp_field_name} = this.#{@field_conf.name};\n"
    code << "                    if (#{@temp_field_name} != null) {\n"
    code << "                        #{@temp_field_name}.unbind()\n"
    code << "                    }\n"
    code << "                    this.#{@field_conf.name} = new #{@field_conf.name}()\n"
    code << "                            .parent(this).index(#{@field_conf.field_index_const_name}).key(#{@field_conf.store_name_const_name})\n"
    code << "                            .load(it);\n"
    code << "                },\n"
    code << "                () -> {\n"
    code << "                    var #{@temp_field_name} = this.#{@field_conf.name};\n"
    code << "                    if (#{@temp_field_name} != null) {\n"
    code << "                        #{@temp_field_name}.unbind()\n"
    code << "                        this.#{@field_conf.name} = null;\n"
    code << "                    }\n"
    code << "                }\n"
    code << "        );\n"
  end

end


require_relative 'load/load_int_generator'
require_relative 'load/load_long_generator'
require_relative 'load/load_double_generator'
require_relative 'load/load_decimal_generator'
require_relative 'load/load_boolean_generator'
require_relative 'load/load_string_generator'
require_relative 'load/load_date_generator'
require_relative 'load/load_time_generator'
require_relative 'load/load_date_time_generator'
require_relative 'load/load_object_id_generator'
require_relative 'load/load_uuid_generator'
require_relative 'load/load_std_list_generator'
require_relative 'load/load_map_generator'
