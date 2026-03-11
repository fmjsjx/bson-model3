require_relative 'clean_field_generator'


class CleanFieldsGenerator

  attr_reader :config, :model_conf, :fields

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @clean_field_generators = model_conf.fields.filter do |field_conf|
      not field_conf.virtual?
    end.map do |field_conf|
      CleanFieldGenerator.from(config, model_conf, field_conf)
    end
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    protected #{@model_conf.name} cleanFields() {\n"
    @clean_field_generators.each do |clean_field_generator|
      code << clean_field_generator.generate
    end
    code << "        return this;\n"
    code << "    }\n"
  end

end


