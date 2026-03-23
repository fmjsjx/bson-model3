require_relative 'load_field_generator'


class LoadGenerator

  attr_reader :config, :model_conf, :load_field_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @load_field_generators = model_conf.fields.select do |field_conf|
      field_conf.store_field? or field_conf.readonly?
    end.map do |field_conf|
      LoadFieldGenerator.from(config, model_conf, field_conf)
    end
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    public #{@model_conf.name} load(BsonDocument src) {\n"
    code << "        resetStates();\n"
    @load_field_generators.each do |load_field_generator|
      code << load_field_generator.generate
    end
    code << "        return this;\n"
    code << "    }\n"
  end

end
