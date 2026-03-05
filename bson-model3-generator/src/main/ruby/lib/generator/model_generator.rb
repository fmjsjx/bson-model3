require_relative 'imports_generator'
require_relative 'consts_generator'
require_relative 'store_data'
require_relative 'properties_generator'


class ModelGenerator

  attr_reader :config,
              :model_conf,
              :imports_generator,
              :consts_generator,
              :properties_generator
  
  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @imports_generator = ImportsGenerator.new(@config, @model_conf)
    @consts_generator = ConstsGenerator.new(@config, @model_conf)
    @properties_generator = PropertiesGenerator.new(@config, @model_conf)
  end

  def generate
    code = generate_class_prefix_code
    code << generate_consts_code
    code << generate_store_data_class
    code << generate_fields_code
    # TODO generate class content
    code << generate_class_suffix_code
  end

  private
  def generate_class_prefix_code
    code = generate_package_code
    code << "\n"
    code << generate_imports_code
    code << "\n"
    code << generate_class_declaration_code
  end

  def generate_package_code
    "package #{@config.package};\n"
  end

  def generate_imports_code
    @imports_generator.generate
  end

  def generate_class_declaration_code
    code = "@NullMarked\n"
    code << "public final class #{@model_conf.name} extends #{generic_super_type} {\n"
  end

  def generic_super_type
    case @model_conf.type
    when 'object'
      "AbstractObjectModel<#{@model_conf.name}>"
    when 'root'
      "AbstractRootModel<#{@model_conf.name}>"
    else
      raise ArgumentError, "Unknown model type: #{@model_conf.type}"
    end
  end

  def generate_consts_code
    @consts_generator.generate
  end

  def generate_store_data_class
    StoreData::generate_class_code(@config, @model_conf)
  end

  def generate_fields_code
    @properties_generator.generate_fields_code
  end

  def generate_class_suffix_code
    "}\n"
  end

end